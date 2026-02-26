package org.arcticquests.dev.oneironaut.Oneironaut.casting.mishaps;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.GarbageIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;

import java.util.List;

public class MishapNoStaff extends Mishap {
    public final Component spellName;

    public MishapNoStaff(Component spellName) {
        super();
        this.spellName = spellName;
    }

    @Override
    public FrozenPigment accentColor(CastingEnvironment ctx, Context errorCtx) {
        return this.dyeColor(DyeColor.PURPLE);
    }

    @Override
    public ParticleSpray particleSpray(CastingEnvironment ctx) {
        // Java requires explicit count in your Hexcasting version.
        return ParticleSpray.burst(ctx.mishapSprayPos(), 1.0, 1);
    }

    @Override
    public Component errorMessage(CastingEnvironment ctx, Context errorCtx) {
        return this.error("oneironaut:nostaff", this.spellName);
    }

    @Override
    public void execute(CastingEnvironment ctx, Context errorCtx, List<Iota> stack) {
        stack.add(new GarbageIota());
        ctx.getMishapEnvironment().dropHeldItems();
        // Vec3 pos = ctx.mishapSprayPos();
        // ctx.getWorld().explode(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, 0.25f, Level.ExplosionInteraction.NONE);
    }

    public static MishapNoStaff of(Component spellName) {
        return new MishapNoStaff(spellName);
    }
}