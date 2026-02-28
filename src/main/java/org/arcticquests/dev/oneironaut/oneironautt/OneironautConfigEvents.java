package org.arcticquests.dev.oneironaut.oneironautt;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber
public final class OneironautConfigEvents {
    private OneironautConfigEvents() {}

    @SubscribeEvent
    public static void onConfigLoading(ModConfigEvent.Loading event) {
        if (event.getConfig().getModId().equals(Oneironaut.MODID)) {
            OneironautConfigForgeBridge.bind();
        }
    }

    @SubscribeEvent
    public static void onConfigReloading(ModConfigEvent.Reloading event) {
        if (event.getConfig().getModId().equals(Oneironaut.MODID)) {
            OneironautConfigForgeBridge.bind();
        }
    }
}