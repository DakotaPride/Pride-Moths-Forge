package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.block.FuzzyCarpetBlock;
import net.dakotapride.pridemoths.block.MothEnclosureBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BlocksRegistrar {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, PrideMothsMod.MOD_ID);

    public static final RegistryObject<FuzzyCarpetBlock> FUZZY_CARPET = BLOCKS.register("fuzzy_carpet",
            () -> new FuzzyCarpetBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_CARPET).sound(SoundType.WOOL)));
    public static RegistryObject<BlockItem> FUZZY_CARPET_ITEM = ItemsRegistrar.ITEMS.register("fuzzy_carpet",
            () -> new BlockItem(BlocksRegistrar.FUZZY_CARPET.get(), new Item.Properties()));

    public static RegistryObject<MothEnclosureBlock> MOTH_ENCLOSURE = BLOCKS.register("moth_enclosure",
            () -> new MothEnclosureBlock(BlockBehaviour.Properties.copy(Blocks.BEEHIVE)));
    public static RegistryObject<BlockItem> MOTH_ENCLOSURE_ITEM = ItemsRegistrar.ITEMS.register("moth_enclosure",
            () -> new BlockItem(BlocksRegistrar.MOTH_ENCLOSURE.get(), new Item.Properties()));

    public static void yep(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
