package org.arcticquests.dev.oneironaut.oneironautt.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.arcticquests.dev.oneironaut.oneironautt.Oneironaut;

public class OneironautFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Oneironaut.MODID);

    public static final RegistryObject<FluidType> THOUGHT_SLURRY_TYPE =
            FLUID_TYPES.register("thought_slurry", () -> new FluidType(
                    FluidType.Properties.create().descriptionId("fluid.oneironaut.thought_slurry").canExtinguish(true)
            ) {
                @Override
                public void initializeClient(java.util.function.Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return ResourceLocation.parse("oneironaut:block/thought_slurry");
                        }
                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return ResourceLocation.parse("oneironaut:block/thought_slurry_flowing");
                        }
                        @Override
                        public ResourceLocation getOverlayTexture() {
                            return ResourceLocation.parse("minecraft:block/water_overlay");
                        }
                    });
                }
            });

    public static void init(IEventBus modEventBus) {
        FLUID_TYPES.register(modEventBus);
    }

}