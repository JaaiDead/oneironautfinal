package org.arcticquests.dev.oneironaut.Oneironaut.status.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.arcticquests.dev.oneironaut.Oneironaut.Oneironaut;

public class OneironautMiscRegistry {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Oneironaut.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Oneironaut.MODID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Oneironaut.MODID);











    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
        EFFECTS.register(eventBus);
        ENCHANTMENTS.register(eventBus);
    }
}
