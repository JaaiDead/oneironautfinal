package org.arcticquests.dev.oneironaut.Oneironaut.casting.patterns.rod;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.sideeffects.EvalSound;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import net.minecraft.network.chat.Component;
import org.arcticquests.dev.oneironaut.Oneironaut.casting.environments.ReverbRodCastEnv;
import org.arcticquests.dev.oneironaut.Oneironaut.casting.mishaps.MishapNoRod;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class OpAccessRAM implements ConstMediaAction {
    private final boolean store;

    public OpAccessRAM(boolean store) {
        this.store = store;
    }

    @Override
    public int getArgc() {
        return this.store ? 1 : 0;
    }

    @Override
    public long getMediaCost() {
        return 0L;
    }

    @Override
    public @NotNull List<Iota> execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment env) throws Mishap {
        if (!(env instanceof ReverbRodCastEnv rodEnv)) {
            throw new MishapNoRod(false);
        }

        if (this.store) {
            Iota iotaToStore = list.get(0);

            boolean isList = iotaToStore.getType() == ListIota.TYPE;
            boolean isDictionaryLike = iotaToStore.getType().toString().toLowerCase().contains("dictionaryiota");

            if (isList || isDictionaryLike) {
                throw new MishapInvalidIota(iotaToStore, 0, Component.translatable("oneironaut.mishap.nolistsallowed"));
            }

            rodEnv.setStoredIota(iotaToStore);
            return Collections.emptyList();
        } else {
            return List.of(rodEnv.getStoredIota());
        }
    }

    @Override
    public @NotNull CostMediaActionResult executeWithOpCount(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) throws Mishap {
        return new CostMediaActionResult(this.execute(list, castingEnvironment), 1L);
    }

    @Override
    public @NotNull OperationResult operate(
            @NotNull CastingEnvironment env,
            @NotNull CastingImage image,
            @NotNull SpellContinuation continuation
    ) {
        List<Iota> stack = image.getStack();
        int argc = this.getArgc();

        int start = Math.max(0, stack.size() - argc);
        List<Iota> args = stack.subList(start, stack.size());

        CostMediaActionResult res = this.executeWithOpCount(args, env);

        List<Iota> newStack = new java.util.ArrayList<>(stack.subList(0, start));
        newStack.addAll(res.getResultStack());

        CastingImage newImage = image.copy(
                newStack,
                image.getParenCount(),
                image.getParenthesized(),
                image.getEscapeNext(),
                image.getOpsConsumed(),
                image.getUserData()
        ).withUsedOps(res.getOpCount());

        return new OperationResult(newImage, Collections.emptyList(), continuation, new EvalSound(null, 0));
    }
}