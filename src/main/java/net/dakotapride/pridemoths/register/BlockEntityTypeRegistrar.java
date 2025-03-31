package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.block.MothEnclosureBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityTypeRegistrar {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, PrideMothsMod.ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MothEnclosureBlockEntity>> MOTH_ENCLOSURE = BLOCK_ENTITY_TYPES.register(
            "moth_enclosure", () -> new BlockEntityType<>(MothEnclosureBlockEntity::new, BlocksRegistrar.MOTH_ENCLOSURE.get()));


    public static void yep(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }
}
