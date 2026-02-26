package org.arcticquests.dev.oneironaut.Oneironaut.casting.mishaps;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.GarbageIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MishapMissingEnchant extends Mishap {
    public final ItemStack stack;
    public final Enchantment enchant;

    public MishapMissingEnchant(ItemStack stack, Enchantment enchant) {
        super();
        this.stack = stack;
        this.enchant = enchant;
    }

    @Override
    public FrozenPigment accentColor(CastingEnvironment ctx, Context errorCtx) {
        return this.dyeColor(DyeColor.PURPLE);
    }

    @Override
    public ParticleSpray particleSpray(CastingEnvironment ctx) {
        var caster = ctx.getCastingEntity();
        if (caster != null) {
            return ParticleSpray.burst(caster.position(), 1.0, 1);
        }
        return ParticleSpray.burst(new Vec3(0.0, 0.0, 0.0), 1.0, 1);
    }

    @Override
    public Component errorMessage(CastingEnvironment ctx, Context errorCtx) {
        return this.error(
                "oneironaut:missingenchant",
                this.stack.getDisplayName(),
                Component.translatable(this.enchant.getDescriptionId())
        );
    }

    @Override
    public void execute(CastingEnvironment ctx, Context errorCtx, List<Iota> stack) {
        stack.add(new GarbageIota());

        var caster = ctx.getCastingEntity();
        if (caster instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) caster;
            int newLevel = Math.max(player.experienceLevel - 3, 0);
            player.experienceLevel = newLevel;
        }
    }

    public static MishapMissingEnchant of(ItemStack stack, Enchantment enchant) {
        return new MishapMissingEnchant(stack, enchant);
    }
}