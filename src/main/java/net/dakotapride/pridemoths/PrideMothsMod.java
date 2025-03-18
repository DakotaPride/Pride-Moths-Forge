package net.dakotapride.pridemoths;

import net.dakotapride.pridemoths.block.FuzzyCarpetBlock;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.client.renderer.MothRenderer;
import net.dakotapride.pridemoths.config.PrideMothsCommonConfig;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PrideMothsMod.ID)
public class PrideMothsMod {
    public static final String ID = "pridemoths";
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ID);

    public static TagKey<Block> LIGHT_SOURCES_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ID, "light_sources"));
    public static TagKey<Item> CAN_MOTH_EAT = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ID, "can_moth_eat"));

    private static ResourceKey<EntityType<?>> keyOf(String id) {
        return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ID, id));
    }

//    public static DeferredHolder<EntityType<?>, EntityType<MothEntity>> MOTH =
//            ENTITY_TYPES.register("moth",
//                    () -> EntityType.Builder.of(MothEntity::new, MobCategory.MONSTER)
//                            .sized(0.3f, 0.3f)
//                            .build(ResourceLocation.fromNamespaceAndPath(MOD_ID, "moth").toString()));
    public static DeferredHolder<EntityType<?>, EntityType<MothEntity>> MOTH = ENTITY_TYPES.register(
            "moth", () -> EntityType.Builder.of(MothEntity::new, MobCategory.CREATURE)
                    .sized(0.45F, 0.45F).build(keyOf("moth")));
    public static DeferredItem<Item> MOTH_SPAWN_EGG = ITEMS.registerItem("moth_spawn_egg",
            settings -> new SpawnEggItem(PrideMothsMod.MOTH.get(), settings));
    public static DeferredItem<Item> MOTH_FUZZ = ITEMS.registerItem("moth_fuzz",
            Item::new);
    public static DeferredItem<Item> FRUITFUL_STEW = ITEMS.registerItem("fruitful_stew",
            settings -> new FruitfulStewFoodItem(settings.stacksTo(1)));
    public static DeferredItem<Item> GLASS_JAR = ITEMS.registerItem("glass_jar",
            settings -> new GlassJarItem(true, settings));
    public static DeferredItem<Item> MOTH_JAR = ITEMS.registerItem("moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> RARE_MOTH_JAR = ITEMS.registerItem(MothVariation.RARE.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> TRANSGENDER_MOTH_JAR = ITEMS.registerItem(MothVariation.TRANSGENDER.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> LGBT_MOTH_JAR = ITEMS.registerItem(MothVariation.LGBT.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> NON_BINARY_MOTH_JAR = ITEMS.registerItem(MothVariation.NON_BINARY.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> LESBIAN_MOTH_JAR = ITEMS.registerItem(MothVariation.LESBIAN.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> GAY_MOTH_JAR = ITEMS.registerItem(MothVariation.GAY.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> AGENDER_MOTH_JAR = ITEMS.registerItem(MothVariation.AGENDER.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> ASEXUAL_MOTH_JAR = ITEMS.registerItem(MothVariation.ASEXUAL.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> PANSEXUAL_MOTH_JAR = ITEMS.registerItem(MothVariation.PANSEXUAL.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> BISEXUAL_MOTH_JAR = ITEMS.registerItem(MothVariation.BISEXUAL.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> POLYAMOROUS_MOTH_JAR = ITEMS.registerItem(MothVariation.POLYAMOROUS.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> POLYSEXUAL_MOTH_JAR = ITEMS.registerItem(MothVariation.POLYSEXUAL.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> OMNISEXUAL_MOTH_JAR = ITEMS.registerItem(MothVariation.OMNISEXUAL.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> AROMANTIC_MOTH_JAR = ITEMS.registerItem(MothVariation.AROMANTIC.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> DEMISEXUAL_MOTH_JAR = ITEMS.registerItem(MothVariation.DEMISEXUAL.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> DEMIBOY_MOTH_JAR = ITEMS.registerItem(MothVariation.DEMIBOY.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> DEMIGIRL_MOTH_JAR = ITEMS.registerItem(MothVariation.DEMIGIRL.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> DEMIGENDER_MOTH_JAR = ITEMS.registerItem(MothVariation.DEMIGENDER.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> AROACE_MOTH_JAR = ITEMS.registerItem(MothVariation.AROACE.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> DEMIROMANTIC_MOTH_JAR = ITEMS.registerItem(MothVariation.DEMIROMANTIC.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> ALLY_MOTH_JAR = ITEMS.registerItem(MothVariation.ALLY.getVariation() + "_moth_jar",
            GlassJarItem::new);
    public static final DeferredBlock<Block> FUZZY_CARPET = BLOCKS.registerBlock("fuzzy_carpet",
            settings -> new FuzzyCarpetBlock(settings.sound(SoundType.WOOL).mapColor(MapColor.TERRACOTTA_WHITE)
                    .strength(0.1F).pushReaction(PushReaction.DESTROY)),
            BlockBehaviour.Properties.of().sound(SoundType.WOOL).mapColor(MapColor.TERRACOTTA_WHITE)
                    .strength(0.1F).pushReaction(PushReaction.DESTROY));
    public static DeferredItem<BlockItem> FUZZY_CARPET_ITEM = ITEMS.registerSimpleBlockItem("fuzzy_carpet", FUZZY_CARPET);

    public PrideMothsMod(IEventBus bus, ModContainer modContainer) {

        bus.addListener(this::commonSetup);

        BLOCKS.register(bus);
        ITEMS.register(bus);
        ENTITY_TYPES.register(bus);

        // GeckoLib.initialize();

        modContainer.registerConfig(ModConfig.Type.COMMON, PrideMothsCommonConfig.SPEC, "pridemoths-common.toml");

        // Register the item to a creative tab
        bus.addListener(this::itemGroupEvent);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
    }

    public void itemGroupEvent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(MOTH_SPAWN_EGG);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(GLASS_JAR);
            event.accept(MOTH_JAR);
            event.accept(RARE_MOTH_JAR);
            event.accept(TRANSGENDER_MOTH_JAR);
            event.accept(LGBT_MOTH_JAR);
            event.accept(NON_BINARY_MOTH_JAR);
            event.accept(LESBIAN_MOTH_JAR);
            event.accept(GAY_MOTH_JAR);
            event.accept(AGENDER_MOTH_JAR);
            event.accept(ASEXUAL_MOTH_JAR);
            event.accept(PANSEXUAL_MOTH_JAR);
            event.accept(BISEXUAL_MOTH_JAR);
            event.accept(POLYAMOROUS_MOTH_JAR);
            event.accept(POLYSEXUAL_MOTH_JAR);
            event.accept(OMNISEXUAL_MOTH_JAR);
            event.accept(AROMANTIC_MOTH_JAR);
            event.accept(DEMISEXUAL_MOTH_JAR);
            event.accept(DEMIBOY_MOTH_JAR);
            event.accept(DEMIGIRL_MOTH_JAR);
            event.accept(DEMIGENDER_MOTH_JAR);
            event.accept(AROACE_MOTH_JAR);
            event.accept(DEMIROMANTIC_MOTH_JAR);
            event.accept(ALLY_MOTH_JAR);
        }
    }

    @EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(PrideMothsMod.MOTH.get(), MothRenderer::new);
        }
    }

    @EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(PrideMothsMod.MOTH.get(), MothEntity.setAttributes().build());
        }
    }
}
