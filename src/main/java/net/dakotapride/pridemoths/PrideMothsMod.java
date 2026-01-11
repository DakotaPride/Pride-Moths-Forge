package net.dakotapride.pridemoths;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.model.BabyMothModel;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.dakotapride.pridemoths.client.renderer.MothRenderer;
import net.dakotapride.pridemoths.config.PrideMothsCommonConfig;
import net.dakotapride.pridemoths.register.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PrideMothsMod.ID)
public class PrideMothsMod {
    public static final String ID = "pridemoths";

    public static TagKey<Block> LIGHT_SOURCES_TAG = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "light_sources"));
    public static TagKey<Block> MOTH_ENCLOSURES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "moth_enclosures"));
    public static TagKey<Item> CAN_MOTH_EAT = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "can_moth_eat"));
    public static TagKey<Item> MOTH_JARS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "moth_jars"));

    public static final IntegerProperty FUZZ_LEVEL = IntegerProperty.create("fuzz_level", 0, 3);

    public static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ID, name));
    }

    public PrideMothsMod(IEventBus bus, ModContainer modContainer) {

        bus.addListener(this::commonSetup);

        ItemsRegistrar.yep(bus);
        BlocksRegistrar.yep(bus);
        BlockEntityTypeRegistrar.yep(bus);
        EntityTypeRegistrar.yep(bus);
        DataComponentsRegistrar.yep(bus);

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
            event.accept(ItemsRegistrar.MOTH_SPAWN_EGG);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ItemsRegistrar.GLASS_JAR);
            event.accept(ItemsRegistrar.MOTH_JAR);
            event.accept(ItemsRegistrar.RARE_MOTH_JAR);
            event.accept(ItemsRegistrar.TRANSGENDER_MOTH_JAR);
            event.accept(ItemsRegistrar.LGBT_MOTH_JAR);
            event.accept(ItemsRegistrar.NON_BINARY_MOTH_JAR);
            event.accept(ItemsRegistrar.LESBIAN_MOTH_JAR);
            event.accept(ItemsRegistrar.GAY_MOTH_JAR);
            event.accept(ItemsRegistrar.AGENDER_MOTH_JAR);
            event.accept(ItemsRegistrar.ASEXUAL_MOTH_JAR);
            event.accept(ItemsRegistrar.PANSEXUAL_MOTH_JAR);
            event.accept(ItemsRegistrar.BISEXUAL_MOTH_JAR);
            event.accept(ItemsRegistrar.POLYAMOROUS_MOTH_JAR);
            event.accept(ItemsRegistrar.POLYSEXUAL_MOTH_JAR);
            event.accept(ItemsRegistrar.OMNISEXUAL_MOTH_JAR);
            event.accept(ItemsRegistrar.AROMANTIC_MOTH_JAR);
            event.accept(ItemsRegistrar.DEMISEXUAL_MOTH_JAR);
            event.accept(ItemsRegistrar.DEMIBOY_MOTH_JAR);
            event.accept(ItemsRegistrar.DEMIGIRL_MOTH_JAR);
            event.accept(ItemsRegistrar.DEMIGENDER_MOTH_JAR);
            event.accept(ItemsRegistrar.AROACE_MOTH_JAR);
            event.accept(ItemsRegistrar.DEMIROMANTIC_MOTH_JAR);
            event.accept(ItemsRegistrar.GENDERFLUID_MOTH_JAR);
            event.accept(ItemsRegistrar.INTERSEX_MOTH_JAR);
            event.accept(ItemsRegistrar.XENOGENDER_MOTH_JAR);
            event.accept(ItemsRegistrar.GENDER_QUEER_MOTH_JAR);
            event.accept(ItemsRegistrar.GENDERFAE_MOTH_JAR);
            event.accept(ItemsRegistrar.GENDERFAUN_MOTH_JAR);
            event.accept(ItemsRegistrar.BIGENDER_MOTH_JAR);
            event.accept(ItemsRegistrar.PANGENDER_MOTH_JAR);
            event.accept(ItemsRegistrar.ALLY_MOTH_JAR);
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(BlocksRegistrar.FUZZY_CARPET);
            event.accept(BlocksRegistrar.MOTH_ENCLOSURE);
        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ItemsRegistrar.MOTH_FUZZ);
        }

        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ItemsRegistrar.FRUITFUL_STEW);
        }
    }

    @EventBusSubscriber(modid = ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(EntityTypeRegistrar.MOTH.get(), MothRenderer::new);
        }
    }

    @EventBusSubscriber(modid = ID)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(EntityTypeRegistrar.MOTH.get(), MothEntity.setAttributes().build());
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(MothModel.LAYER_LOCATION, MothModel::createBodyLayer);
            event.registerLayerDefinition(BabyMothModel.LAYER_LOCATION, BabyMothModel::createBodyLayer);
        }
    }
}
