package org.arcticquests.dev.oneironaut.Oneironaut.casting.mishaps;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.GarbageIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

import java.util.List;

public class MishapBadCuboid extends Mishap {
    public final String stub;

    public MishapBadCuboid(String stub) {
        this.stub = stub;
    }

    @Override
    public FrozenPigment accentColor(CastingEnvironment ctx, Context errorCtx) {
        return this.dyeColor(DyeColor.PURPLE);
    }

    @Override
    public ParticleSpray particleSpray(CastingEnvironment ctx) {
        return ParticleSpray.burst(ctx.mishapSprayPos(), 1.0, 1);
    }

    @Override
    public Component errorMessage(CastingEnvironment ctx, Context errorCtx) {
        return this.error("oneironaut:badcuboid." + this.stub);
    }

    @Override
    public void execute(CastingEnvironment env, Context errorCtx, List<Iota> stack) {
        stack.add(new GarbageIota());
        env.getWorld().explode(
                null,
                env.mishapSprayPos().x,
                env.mishapSprayPos().y,
                env.mishapSprayPos().z,
                0.25f,
                Level.ExplosionInteraction.NONE
        );
    }

    public static MishapBadCuboid of(String stub) {
        return new MishapBadCuboid(stub);
    }
}