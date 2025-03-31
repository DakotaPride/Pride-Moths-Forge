package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ItemsRegistrar {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PrideMothsMod.ID);

    public static DeferredItem<Item> MOTH_SPAWN_EGG = ITEMS.registerItem("moth_spawn_egg",
            settings -> new SpawnEggItem(EntityTypeRegistrar.MOTH.get(), settings));
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

    public static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item, Supplier<Item.Properties> properties) {
        return ITEMS.register(name, () -> item.apply(properties.get().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, name)))));
    }

    public static void yep(IEventBus bus) {
        ITEMS.register(bus);
    }
}
