package org.arcticquests.dev.oneironaut.Oneironaut.registry;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.arcticquests.dev.oneironaut.Oneironaut.Oneironaut;
import org.arcticquests.dev.oneironaut.Oneironaut.block.ThoughtSlurry;
import org.arcticquests.dev.oneironaut.Oneironaut.casting.OvercastDamageEnchant;
import org.arcticquests.dev.oneironaut.Oneironaut.status.*;

public class OneironautMiscRegistry {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID,Oneironaut.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create( Registries.MOB_EFFECT,Oneironaut.MODID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create( Registries.ENCHANTMENT,Oneironaut.MODID);


    //I will not scream at my computer over this

    public static void init(IEventBus bus) {
        FLUIDS.register(bus);
        EFFECTS.register(bus);
        ENCHANTMENTS.register(bus);
    }

    public static final RegistryObject<DetectionResistEffect> DETECTION_RESISTANCE = EFFECTS.register("detection_resistance", DetectionResistEffect::new);
    public static final RegistryObject<GlowingAmbitEffect> NOT_MISSING = EFFECTS.register("not_missing", GlowingAmbitEffect::new);
    public static final RegistryObject<MonkfruitDelayEffect> RUMINATION = EFFECTS.register("rumination", MonkfruitDelayEffect::new);
    public static final RegistryObject<MediaDisintegrationEffect> DISINTEGRATION = EFFECTS.register("disintegration", MediaDisintegrationEffect::new);
    public static final RegistryObject<DisintegrationProtectionEffect> DISINTEGRATION_PROTECTION = EFFECTS.register("disintegration_protection", DisintegrationProtectionEffect::new);

    public static final RegistryObject<ThoughtSlurry> THOUGHT_SLURRY = FLUIDS.register("thought_slurry", () -> ThoughtSlurry.STILL_FLUID /*new ThoughtSlurry.Still(OneironautThingRegistry.THOUGHT_SLURRY_ATTRIBUTES)*/);
    public static final RegistryObject<ThoughtSlurry> THOUGHT_SLURRY_FLOWING = FLUIDS.register("thought_slurry_flowing", () -> ThoughtSlurry.FLOWING_FLUID /*new ThoughtSlurry.Flowing(OneironautThingRegistry.THOUGHT_SLURRY_ATTRIBUTES)*/);

    public static final RegistryObject<OvercastDamageEnchant> OVERCAST_DAMAGE_ENCHANT = ENCHANTMENTS.register("overcast_damage", OvercastDamageEnchant::new);
}
