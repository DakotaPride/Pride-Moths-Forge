package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.item.FruitfulStewFoodItem;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
            settings -> new SpawnEggItem(settings.spawnEgg(EntityTypeRegistrar.MOTH.get())));
    public static DeferredItem<Item> MOTH_FUZZ = ITEMS.registerItem("moth_fuzz",
            Item::new);
    public static DeferredItem<Item> FRUITFUL_STEW = ITEMS.registerItem("fruitful_stew",
            settings -> new FruitfulStewFoodItem(settings.stacksTo(1)));
    public static DeferredItem<Item> GLASS_JAR = ITEMS.registerItem("glass_jar",
            settings -> new GlassJarItem(true, settings));
    public static DeferredItem<Item> MOTH_JAR = ITEMS.registerItem("moth_jar",
            GlassJarItem::new);
    public static DeferredItem<Item> RARE_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.RARE);
    public static DeferredItem<Item> TRANSGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.TRANSGENDER);
    public static DeferredItem<Item> LGBT_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.LGBT);
    public static DeferredItem<Item> NON_BINARY_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.NON_BINARY);
    public static DeferredItem<Item> LESBIAN_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.LESBIAN);
    public static DeferredItem<Item> GAY_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GAY);
    public static DeferredItem<Item> AGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.AGENDER);
    public static DeferredItem<Item> ASEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.ASEXUAL);
    public static DeferredItem<Item> PANSEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.PANSEXUAL);
    public static DeferredItem<Item> BISEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.BISEXUAL);
    public static DeferredItem<Item> POLYAMOROUS_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.POLYAMOROUS);
    public static DeferredItem<Item> POLYSEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.POLYSEXUAL);
    public static DeferredItem<Item> OMNISEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.OMNISEXUAL);
    public static DeferredItem<Item> AROMANTIC_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.AROMANTIC);
    public static DeferredItem<Item> DEMISEXUAL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMISEXUAL);
    public static DeferredItem<Item> DEMIBOY_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMIBOY);
    public static DeferredItem<Item> DEMIGIRL_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMIGIRL);
    public static DeferredItem<Item> DEMIGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMIGENDER);
    public static DeferredItem<Item> AROACE_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.AROACE);
    public static DeferredItem<Item> DEMIROMANTIC_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.DEMIROMANTIC);
    public static DeferredItem<Item> GENDERFLUID_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GENDERFLUID);
    public static DeferredItem<Item> INTERSEX_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.INTERSEX);
    public static DeferredItem<Item> XENOGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.XENOGENDER);
    public static DeferredItem<Item> GENDER_QUEER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GENDER_QUEER);
    public static DeferredItem<Item> GENDERFAE_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GENDERFAE);
    public static DeferredItem<Item> GENDERFAUN_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.GENDERFAUN);
    public static DeferredItem<Item> BIGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.BIGENDER);
    public static DeferredItem<Item> PANGENDER_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.PANGENDER);
    public static DeferredItem<Item> ALLY_MOTH_JAR = registerMothContainedWithinJarItem(MothVariation.ALLY);

    public static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item, Supplier<Item.Properties> properties) {
        return ITEMS.register(name, () -> item.apply(properties.get().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, name)))));
    }

    private static DeferredItem<Item> registerMothContainedWithinJarItem(MothVariation variation) {
        return ITEMS.registerItem(variation.getVariation() + "_moth_jar", GlassJarItem::new);
    }

    public static void yep(IEventBus bus) {
        ITEMS.register(bus);
    }
}
