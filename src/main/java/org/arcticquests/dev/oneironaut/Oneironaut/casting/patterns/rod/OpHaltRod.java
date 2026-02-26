package org.arcticquests.dev.oneironaut.Oneironaut.casting.patterns.rod;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.sideeffects.EvalSound;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import org.arcticquests.dev.oneironaut.Oneironaut.casting.environments.ReverbRodCastEnv;
import org.arcticquests.dev.oneironaut.Oneironaut.casting.mishaps.MishapNoRod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpHaltRod implements ConstMediaAction {
    private final int reset;

    public OpHaltRod(int reset) {
        this.reset = reset;
    }

    @Override
    public int getArgc() {
        return this.reset;
    }

    @Override
    public long getMediaCost() {
        return 0L;
    }

    @Override
    public @NotNull List<Iota> execute(@NotNull List<? extends Iota> args, @NotNull CastingEnvironment env) throws Mishap {
        if (env instanceof ReverbRodCastEnv rodEnv) {
            if (this.reset == 1) {
                int delay = OperatorUtils.getPositiveInt(args, 0, this.getArgc());
                rodEnv.setResetCooldown(Math.max(1, Math.min(100, delay)));
            }
            rodEnv.stopCasting();
            return Collections.emptyList();
        }

        throw new MishapNoRod(false);
    }

    @Override
    public @NotNull CostMediaActionResult executeWithOpCount(
            @NotNull List<? extends Iota> list,
            @NotNull CastingEnvironment env
    ) throws Mishap {
        @SuppressWarnings("unchecked")
        List<Iota> out = (List<Iota>) (List<?>) this.execute(list, env);
        return new CostMediaActionResult(out, 1L);
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
        List<Iota> taken = stack.subList(start, stack.size());

        CostMediaActionResult res;
        try {
            res = this.executeWithOpCount(taken, env);
        } catch (Mishap m) {
            throw new RuntimeException(m);
        }

        List<Iota> newStack = new ArrayList<>(stack.subList(0, start));
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