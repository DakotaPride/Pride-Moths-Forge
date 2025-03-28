package net.dakotapride.pridemoths;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.renderer.MothRenderer;
import net.dakotapride.pridemoths.config.PrideMothsCommonConfig;
import net.dakotapride.pridemoths.register.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PrideMothsMod.MOD_ID)
public class PrideMothsMod {
    public static final String MOD_ID = "pridemoths";

    public static TagKey<Block> LIGHT_SOURCES_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation("pridemoths", "light_sources"));
    public static TagKey<Block> MOTH_ENCLOSURES = TagKey.create(Registries.BLOCK, new ResourceLocation("pridemoths", "moth_enclosures"));
    public static TagKey<Item> CAN_MOTH_EAT = TagKey.create(Registries.ITEM, new ResourceLocation("pridemoths", "can_moth_eat"));
    public static TagKey<Item> MOTH_JARS = TagKey.create(Registries.ITEM, new ResourceLocation("pridemoths", "moth_jars"));

    public static final IntegerProperty FUZZ_LEVEL = IntegerProperty.create("fuzz_level", 0, 3);

    public PrideMothsMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::commonSetup);

        ItemsRegistrar.yep(bus);
        BlocksRegistrar.yep(bus);
        BlockEntityTypeRegistrar.yep(bus);
        EntityTypeRegistrar.yep(bus);
        DataComponentsRegistrar.yep();

        GeckoLib.initialize();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PrideMothsCommonConfig.SPEC, "pridemoths-common.toml");

        // Register the item to a creative tab
        bus.addListener(this::itemGroupEvent);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
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

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(EntityTypeRegistrar.MOTH.get(), MothRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(EntityTypeRegistrar.MOTH.get(), MothEntity.setAttributes().build());
        }
    }
}
