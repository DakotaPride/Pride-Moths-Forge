package net.dakotapride.pridemoths.config;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = PrideMothsMod.ID, value = Dist.DEDICATED_SERVER)
public class PrideMothsCommonConfig {

    PrideMothsCommonConfig(ModConfigSpec.Builder builder) {
        // Define values here in final fields
    }

    // Somewhere the constructor is accessible


    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH;
    public static ModConfigSpec.IntValue BASE_RARE_CHANCE;
    public static ModConfigSpec.IntValue BASE_RARE_CHANCE_MOTH_WEEK;

    public static boolean pride_moths_outside_of_pride_moth;
    public static int base_rare_chance;
    public static int moth_week_rare_chance;

    static {
        BUILDER.push("Configs for Happy Pride Moth!");

        GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH = BUILDER
                .comment("Determines whether or not pride variants for moths spawn naturally outside of Pride Month")
                .comment("Default value: false")
                .define("Spawn Pride Variants Outside of Pride Month", false);
        BASE_RARE_CHANCE = BUILDER
                .comment("Determines the default value for the rare, golden moth variant")
                .comment("The integer used will be used like this: 1 out of <value> chance")
                .comment("Default value: 240")
                .defineInRange("Chance of a Moth Spawning as the Rare Variant", 240, 0, Integer.MAX_VALUE);
        BASE_RARE_CHANCE_MOTH_WEEK = BUILDER
                .comment("Determines the default value for the rare, golden moth variant during moth week (last week of July)")
                .comment("The integer used will be used like this: 1 out of <value> chance")
                .comment("Default value: 120")
                .defineInRange("Chance of a Moth Spawning as the Rare Variant During Moth Week", 120, 0, Integer.MAX_VALUE);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        pride_moths_outside_of_pride_moth = GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH.get();
        base_rare_chance = BASE_RARE_CHANCE.get();
        moth_week_rare_chance = BASE_RARE_CHANCE_MOTH_WEEK.get();
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {}
}
