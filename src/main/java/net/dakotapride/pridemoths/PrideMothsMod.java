package net.dakotapride.pridemoths;

import net.dakotapride.pridemoths.block.FuzzyCarpetBlock;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.client.renderer.MothRenderer;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PrideMothsMod.MOD_ID)
public class PrideMothsMod {
    public static final String MOD_ID = "pridemoths";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static TagKey<Block> LIGHT_SOURCES_TAG = TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation("pridemoths", "light_sources"));
    public static TagKey<Item> CAN_MOTH_EAT = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("pridemoths", "can_moth_eat"));
    public static TagKey<Item> DAMAGES_MOTH_UPON_CONSUMPTION = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("pridemoths", "damages_moth_upon_consumption"));
    public static TagKey<Item> KILLS_MOTH_UPON_CONSUMPTION = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("pridemoths", "kills_moth_upon_consumption"));

    public static RegistryObject<EntityType<MothEntity>> MOTH =
            ENTITY_TYPES.register("moth",
                    () -> EntityType.Builder.of(MothEntity::new, MobCategory.MONSTER)
                            .sized(0.3f, 0.3f)
                            .build(new ResourceLocation(MOD_ID, "moth").toString()));
    public static RegistryObject<Item> MOTH_SPAWN_EGG = ITEMS.register("moth_spawn_egg",
            () -> new ForgeSpawnEggItem(() -> PrideMothsMod.MOTH.get(), 0xCECAC4, 0x82635C, new Item.Properties()));
    public static RegistryObject<Item> MOTH_FUZZ = ITEMS.register("moth_fuzz",
            () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> FRUITFUL_STEW = ITEMS.register("fruitful_stew",
            () -> new FruitfulStewFoodItem(new Item.Properties().stacksTo(1)));
    public static RegistryObject<Item> GLASS_JAR = ITEMS.register("glass_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> MOTH_JAR = ITEMS.register("moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> RARE_MOTH_JAR = ITEMS.register(MothVariation.RARE.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> TRANSGENDER_MOTH_JAR = ITEMS.register(MothVariation.TRANSGENDER.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> LGBT_MOTH_JAR = ITEMS.register(MothVariation.LGBT.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> NON_BINARY_MOTH_JAR = ITEMS.register(MothVariation.NON_BINARY.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> LESBIAN_MOTH_JAR = ITEMS.register(MothVariation.LESBIAN.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> GAY_MOTH_JAR = ITEMS.register(MothVariation.GAY.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> AGENDER_MOTH_JAR = ITEMS.register(MothVariation.AGENDER.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> ASEXUAL_MOTH_JAR = ITEMS.register(MothVariation.ASEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> PANSEXUAL_MOTH_JAR = ITEMS.register(MothVariation.PANSEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> BISEXUAL_MOTH_JAR = ITEMS.register(MothVariation.BISEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> POLYAMOROUS_MOTH_JAR = ITEMS.register(MothVariation.POLYAMOROUS.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> POLYSEXUAL_MOTH_JAR = ITEMS.register(MothVariation.POLYSEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> OMNISEXUAL_MOTH_JAR = ITEMS.register(MothVariation.OMNISEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> AROMANTIC_MOTH_JAR = ITEMS.register(MothVariation.AROMANTIC.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> DEMISEXUAL_MOTH_JAR = ITEMS.register(MothVariation.DEMISEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> DEMIBOY_MOTH_JAR = ITEMS.register(MothVariation.DEMIBOY.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> DEMIGIRL_MOTH_JAR = ITEMS.register(MothVariation.DEMIGIRL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> DEMIGENDER_MOTH_JAR = ITEMS.register(MothVariation.DEMIGENDER.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> AROACE_MOTH_JAR = ITEMS.register(MothVariation.AROACE.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> DEMIROMANTIC_MOTH_JAR = ITEMS.register(MothVariation.DEMIROMANTIC.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Item> ALLY_MOTH_JAR = ITEMS.register(MothVariation.ALLY.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static RegistryObject<Block> FUZZY_CARPET = BLOCKS.register("fuzzy_carpet",
            () -> new FuzzyCarpetBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_CARPET).sound(SoundType.WOOL)));
    public static RegistryObject<BlockItem> FUZZY_CARPET_ITEM = ITEMS.register("fuzzy_carpet",
            () -> new BlockItem(PrideMothsMod.FUZZY_CARPET.get(), new Item.Properties()));

    public PrideMothsMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::commonSetup);

        BLOCKS.register(bus);
        ITEMS.register(bus);
        ENTITY_TYPES.register(bus);

        GeckoLib.initialize();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) { }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(PrideMothsMod.MOTH.get(), MothRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(PrideMothsMod.MOTH.get(), MothEntity.setAttributes().build());
        }
    }
}
