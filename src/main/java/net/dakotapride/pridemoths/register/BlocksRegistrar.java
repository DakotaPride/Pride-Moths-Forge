package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.block.FuzzyCarpetBlock;
import net.dakotapride.pridemoths.block.MothEnclosureBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlocksRegistrar {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PrideMothsMod.ID);

    public static final DeferredBlock<Block> FUZZY_CARPET = fuzzyCarpet(FuzzyCarpetBlock::new,
            () -> BlockBehaviour.Properties.of().sound(SoundType.WOOL).mapColor(MapColor.TERRACOTTA_WHITE)
                    .strength(0.1F).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> MOTH_ENCLOSURE = mothEnclosure(MothEnclosureBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_PINK));
    //public static DeferredItem<BlockItem> FUZZY_CARPET_ITEM = ITEMS.registerSimpleBlockItem("fuzzy_carpet", FUZZY_CARPET);

    public static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name,
                                                                           Function<BlockBehaviour.Properties, T> block,
                                                                           Supplier<BlockBehaviour.Properties> properties) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, () -> block.apply(properties.get().setId(ResourceKey.create(Registries.BLOCK,
                Identifier.fromNamespaceAndPath(PrideMothsMod.ID, name)))));
        ItemsRegistrar.register(
                name,
                itemProps -> new BlockItem(toReturn.get(), itemProps),
                () -> new Item.Properties().useBlockDescriptionPrefix());
        return toReturn;
    }

    public static <T extends Block> DeferredBlock<T> fuzzyCarpet(Function<BlockBehaviour.Properties, T> block,
                                                                           Supplier<BlockBehaviour.Properties> properties) {
        DeferredBlock<T> toReturn = BLOCKS.register("fuzzy_carpet", () -> block.apply(properties.get().setId(ResourceKey.create(Registries.BLOCK,
                Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "fuzzy_carpet")))));
        ItemsRegistrar.register(
                "fuzzy_carpet",
                itemProps -> new FuzzyCarpetBlock.FuzzyCarpetBlockItem(toReturn.get(), itemProps),
                () -> new Item.Properties().useBlockDescriptionPrefix());
        return toReturn;
    }

    public static <T extends Block> DeferredBlock<T> mothEnclosure(Function<BlockBehaviour.Properties, T> block,
                                                                           Supplier<BlockBehaviour.Properties> properties) {
        DeferredBlock<T> toReturn = BLOCKS.register("moth_enclosure", () -> block.apply(properties.get().setId(ResourceKey.create(Registries.BLOCK,
                Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "moth_enclosure")))));
        ItemsRegistrar.register(
                "moth_enclosure",
                itemProps -> new MothEnclosureBlock.MothEnclosureBlockItem(toReturn.get(), itemProps),
                () -> new Item.Properties().useBlockDescriptionPrefix());
        return toReturn;
    }

    public static void yep(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
