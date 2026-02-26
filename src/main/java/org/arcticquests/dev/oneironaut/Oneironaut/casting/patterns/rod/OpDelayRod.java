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
import org.arcticquests.dev.oneironaut.Oneironaut.registry.OneironautItemRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpDelayRod implements ConstMediaAction {
    @Override
    public int getArgc() {
        return 1;
    }

    @Override
    public long getMediaCost() {
        return 0L;
    }

    @Override
    public @NotNull List<Iota> execute(
            @NotNull List<? extends Iota> args,
            @NotNull CastingEnvironment env
    ) throws Mishap {
        int delay = OperatorUtils.getPositiveInt(args, 0, this.getArgc());
        OneironautItemRegistry.REVERBERATION_ROD.get();

        if (env instanceof ReverbRodCastEnv rodEnv) {
            rodEnv.setDelay(delay);
            return Collections.emptyList();
        }

        throw new MishapNoRod(false);
    }

    @Override
    public @NotNull CostMediaActionResult executeWithOpCount(
            @NotNull List<? extends Iota> list,
            @NotNull CastingEnvironment env
    ) throws Mishap {
        List<Iota> out = this.execute(list, env);
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
            int argc = this.getArgc();
            List<Iota> stack = image.getStack();
            int start = Math.max(0, stack.size() - argc);
            List<Iota> taken = stack.subList(start, stack.size());

            res = this.executeWithOpCount(taken, env);

            List<Iota> newStack = new ArrayList<>(stack.subList(0, start));
            newStack.addAll(res.getResultStack());

            var newImage = image.copy(
                    newStack,
                    image.getParenCount(),
                    image.getParenthesized(),
                    image.getEscapeNext(),
                    image.getOpsConsumed(),
                    image.getUserData()
            ).withUsedOps(res.getOpCount());

            return new OperationResult(
                    newImage,
                    Collections.emptyList(),
                    continuation,
                    new EvalSound(null, 0)
            );
        } catch (Mishap m) {
            throw new RuntimeException(m);
        }
    }
}