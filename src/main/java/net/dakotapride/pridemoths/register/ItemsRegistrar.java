package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemsRegistrar {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PrideMothsMod.MOD_ID);

    public static DeferredItem<Item> MOTH_SPAWN_EGG = ITEMS.register("moth_spawn_egg",
            () -> new DeferredSpawnEggItem(() -> EntityTypeRegistrar.MOTH.get(), 0xCECAC4, 0x82635C, new Item.Properties()));
    public static DeferredItem<Item> MOTH_FUZZ = ITEMS.register("moth_fuzz",
            () -> new Item(new Item.Properties()));
    public static DeferredItem<Item> FRUITFUL_STEW = ITEMS.register("fruitful_stew",
            () -> new FruitfulStewFoodItem(new Item.Properties().stacksTo(1)));
    public static DeferredItem<Item> GLASS_JAR = ITEMS.register("glass_jar",
            () -> new GlassJarItem(true, new Item.Properties()));
    public static DeferredItem<Item> MOTH_JAR = ITEMS.register("moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> RARE_MOTH_JAR = ITEMS.register(MothVariation.RARE.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> TRANSGENDER_MOTH_JAR = ITEMS.register(MothVariation.TRANSGENDER.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> LGBT_MOTH_JAR = ITEMS.register(MothVariation.LGBT.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> NON_BINARY_MOTH_JAR = ITEMS.register(MothVariation.NON_BINARY.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> LESBIAN_MOTH_JAR = ITEMS.register(MothVariation.LESBIAN.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> GAY_MOTH_JAR = ITEMS.register(MothVariation.GAY.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> AGENDER_MOTH_JAR = ITEMS.register(MothVariation.AGENDER.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> ASEXUAL_MOTH_JAR = ITEMS.register(MothVariation.ASEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> PANSEXUAL_MOTH_JAR = ITEMS.register(MothVariation.PANSEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> BISEXUAL_MOTH_JAR = ITEMS.register(MothVariation.BISEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> POLYAMOROUS_MOTH_JAR = ITEMS.register(MothVariation.POLYAMOROUS.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> POLYSEXUAL_MOTH_JAR = ITEMS.register(MothVariation.POLYSEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> OMNISEXUAL_MOTH_JAR = ITEMS.register(MothVariation.OMNISEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> AROMANTIC_MOTH_JAR = ITEMS.register(MothVariation.AROMANTIC.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> DEMISEXUAL_MOTH_JAR = ITEMS.register(MothVariation.DEMISEXUAL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> DEMIBOY_MOTH_JAR = ITEMS.register(MothVariation.DEMIBOY.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> DEMIGIRL_MOTH_JAR = ITEMS.register(MothVariation.DEMIGIRL.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> DEMIGENDER_MOTH_JAR = ITEMS.register(MothVariation.DEMIGENDER.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> AROACE_MOTH_JAR = ITEMS.register(MothVariation.AROACE.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> DEMIROMANTIC_MOTH_JAR = ITEMS.register(MothVariation.DEMIROMANTIC.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));
    public static DeferredItem<Item> ALLY_MOTH_JAR = ITEMS.register(MothVariation.ALLY.getVariation() + "_moth_jar",
            () -> new GlassJarItem(new Item.Properties()));

    public static void yep(IEventBus bus) {
        ITEMS.register(bus);
    }
}
