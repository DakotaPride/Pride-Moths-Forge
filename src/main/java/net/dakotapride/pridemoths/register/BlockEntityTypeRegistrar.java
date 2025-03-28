package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.block.MothEnclosureBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeRegistrar {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, PrideMothsMod.MOD_ID);

    public static RegistryObject<BlockEntityType<? extends MothEnclosureBlockEntity>> MOTH_ENCLOSURE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("moth_enclosure",
            () -> BlockEntityType.Builder.of(MothEnclosureBlockEntity::new, BlocksRegistrar.MOTH_ENCLOSURE.get()).build(null));

    public static void yep(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }
}
