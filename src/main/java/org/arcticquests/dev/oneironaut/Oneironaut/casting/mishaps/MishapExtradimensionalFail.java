package org.arcticquests.dev.oneironaut.Oneironaut.casting.mishaps;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;

import java.util.List;

public class MishapExtradimensionalFail extends Mishap {
    public MishapExtradimensionalFail() {
        super();
    }

    @Override
    public FrozenPigment accentColor(CastingEnvironment ctx, Context errorCtx) {
        return this.dyeColor(DyeColor.RED);
    }

    @Override
    public Component errorMessage(CastingEnvironment ctx, Context errorCtx) {
        return null;
    }

    @Override
    public void execute(CastingEnvironment env, Context errorCtx, List<Iota> stack) {
        // intentionally empty (matches Kotlin)
    }
}