package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.block.FuzzyCarpetBlock;
import net.dakotapride.pridemoths.block.MothEnclosureBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlocksRegistrar {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PrideMothsMod.MOD_ID);

    public static final DeferredBlock<FuzzyCarpetBlock> FUZZY_CARPET = BLOCKS.register("fuzzy_carpet",
            () -> new FuzzyCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).sound(SoundType.WOOL)));
    public static DeferredItem<BlockItem> FUZZY_CARPET_ITEM = ItemsRegistrar.ITEMS.register("fuzzy_carpet",
            () -> new BlockItem(BlocksRegistrar.FUZZY_CARPET.get(), new Item.Properties()));

    public static DeferredBlock<MothEnclosureBlock> MOTH_ENCLOSURE = BLOCKS.register("moth_enclosure",
            () -> new MothEnclosureBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE)));
    public static DeferredItem<BlockItem> MOTH_ENCLOSURE_ITEM = ItemsRegistrar.ITEMS.register("moth_enclosure",
            () -> new BlockItem(BlocksRegistrar.MOTH_ENCLOSURE.get(), new Item.Properties()));

    public static void yep(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
