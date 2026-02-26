package org.arcticquests.dev.oneironaut.Oneironaut.status.registry;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.arcticquests.dev.oneironaut.Oneironaut.Oneironaut;

public class OneironautFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Oneironaut.MODID);


    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
