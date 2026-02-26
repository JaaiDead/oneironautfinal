package org.arcticquests.dev.oneironaut.Oneironaut.casting.patterns.rod;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.sideeffects.EvalSound;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.NullIota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.arcticquests.dev.oneironaut.Oneironaut.casting.environments.ReverbRodCastEnv;
import org.arcticquests.dev.oneironaut.Oneironaut.casting.mishaps.MishapNoRod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpGetInitialRodState implements ConstMediaAction {
    private final int mode;

    public OpGetInitialRodState(int mode) {
        this.mode = mode;
    }

    @Override
    public int getArgc() {
        return 0;
    }

    @Override
    public long getMediaCost() {
        return 0L;
    }

    @Override
    public @NotNull List<Iota> execute(@NotNull List<? extends Iota> args, @NotNull CastingEnvironment env) throws Mishap {
        if (!(env instanceof ReverbRodCastEnv rodEnv)) {
            throw new MishapNoRod(false);
        }

        LivingEntity caster = rodEnv.getCastingEntity();
        if (caster == null) {
            throw new MishapNoRod(false);
        }

        ItemStack rodStack = caster.getUseItem();
        if (rodStack.isEmpty()) {
            return List.of(new NullIota());
        }

        CompoundTag rodNbt = rodStack.getTag();
        if (rodNbt == null) {
            return List.of(new NullIota());
        }

        return switch (this.mode) {
            case 1 -> List.of(new Vec3Iota(rodEnv.getInitialLook()));
            case 2 -> List.of(new Vec3Iota(rodEnv.getInitialPos()));
            case 3 -> List.of(new DoubleIota((double) rodEnv.getTimestamp()));
            default -> List.of(new NullIota());
        };
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
        CostMediaActionResult res;
        try {
            res = this.executeWithOpCount(Collections.emptyList(), env);
        } catch (Mishap m) {
            throw new RuntimeException(m);
        }

        List<Iota> newStack = new ArrayList<>(image.getStack());
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