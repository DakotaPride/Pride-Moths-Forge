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


    private static DeferredItem<Item> registerMothContainedWithinJarItem(MothVariation variation) {
        return ITEMS.register(variation.getVariation() + "_moth_jar", () -> new GlassJarItem(new Item.Properties()));
    }

    public static void yep(IEventBus bus) {
        ITEMS.register(bus);
    }
}
