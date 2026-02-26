package org.arcticquests.dev.oneironaut.Oneironaut.casting.mishaps;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.GarbageIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MishapUninfusable extends Mishap {
    public final BlockPos pos;

    public MishapUninfusable(BlockPos pos) {
        super();
        this.pos = pos;
    }

    @Override
    public FrozenPigment accentColor(CastingEnvironment ctx, Context errorCtx) {
        return this.dyeColor(DyeColor.PURPLE);
    }

    @Override
    public ParticleSpray particleSpray(CastingEnvironment ctx) {
        return ParticleSpray.burst(Vec3.atCenterOf(this.pos), 1.0, 1);
    }

    @Override
    public Component errorMessage(CastingEnvironment ctx, Context errorCtx) {
        return this.error(
                "oneironaut:uninfusable",
                this.pos.toShortString(),
                this.blockAtPos(ctx, this.pos)
        );
    }

    @Override
    public void execute(CastingEnvironment ctx, Context errorCtx, List<Iota> stack) {
        stack.add(new GarbageIota());
        ctx.getWorld().explode(
                null,
                this.pos.getX() + 0.5,
                this.pos.getY() + 0.5,
                this.pos.getZ() + 0.5,
                0.25f,
                Level.ExplosionInteraction.NONE
        );
    }

    public static MishapUninfusable of(BlockPos pos) {
        return new MishapUninfusable(pos);
    }
}