package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegistrar {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, PrideMothsMod.MOD_ID);

    public static RegistryObject<Item> MOTH_SPAWN_EGG = ITEMS.register("moth_spawn_egg",
            () -> new ForgeSpawnEggItem(() -> EntityTypeRegistrar.MOTH.get(), 0xCECAC4, 0x82635C, new Item.Properties()));
    public static RegistryObject<Item> MOTH_FUZZ = ITEMS.register("moth_fuzz",
            () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> FRUITFUL_STEW = ITEMS.register("fruitful_stew",
            () -> new FruitfulStewFoodItem(new Item.Properties().stacksTo(1)));
    public static RegistryObject<Item> GLASS_JAR = ITEMS.register("glass_jar",
            () -> new GlassJarItem(true, new Item.Properties()));
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

    public static void yep(IEventBus bus) {
        ITEMS.register(bus);
    }
}
