package org.arcticquests.dev.oneironaut.Oneironaut.status.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.arcticquests.dev.oneironaut.Oneironaut.Oneironaut;
import org.arcticquests.dev.oneironaut.Oneironaut.block.blockentity.CellEntity;

public class OneironautBlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Oneironaut.MODID);

    public static final RegistryObject<BlockEntityType<CellEntity>> CELL_ENTITY =
            BLOCK_ENTITIES.register("cell_entity", () -> BlockEntityType.Builder.of(CellEntity::new, OneironautBlockRegistry.CELL.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
