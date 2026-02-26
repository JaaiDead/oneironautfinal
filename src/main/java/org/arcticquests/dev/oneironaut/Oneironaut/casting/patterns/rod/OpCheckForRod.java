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
import org.arcticquests.dev.oneironaut.Oneironaut.MiscAPIKt;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class OpCheckForRod implements ConstMediaAction {
    @Override
    public int getArgc() {
        return 0;
    }

    @Override
    public long getMediaCost() {
        return 0L;
    }

    @Override
    public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) {
        return (List<Iota>) (List<?>) OperatorUtils.getAsActionResult(MiscAPIKt.isUsingRod(env));
    }

    @Override
    public @NotNull CostMediaActionResult executeWithOpCount(
            @NotNull List<? extends Iota> list,
            @NotNull CastingEnvironment env
    ) throws Mishap {
        @SuppressWarnings("unchecked")
        List<Iota> args = (List<Iota>) list;
        List<Iota> out = this.execute(args, env);
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

        List<Iota> newStack = new java.util.ArrayList<>(image.getStack());
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