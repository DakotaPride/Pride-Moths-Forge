package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.block.MothEnclosureBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityTypeRegistrar {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PrideMothsMod.MOD_ID);

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<MothEnclosureBlockEntity>> MOTH_ENCLOSURE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("moth_enclosure",
            () -> BlockEntityType.Builder.of(MothEnclosureBlockEntity::new, BlocksRegistrar.MOTH_ENCLOSURE.get()).build(null));

    public static void yep(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }
}
