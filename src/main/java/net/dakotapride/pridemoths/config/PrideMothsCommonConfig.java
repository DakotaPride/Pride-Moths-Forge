package net.dakotapride.pridemoths.config;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = PrideMothsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PrideMothsCommonConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC = BUILDER.build();

    public static final ModConfigSpec.BooleanValue GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH = BUILDER
            .comment("Determines whether or not pride variants for moths spawn naturally outside of Pride Month")
            .comment("Default value: false")
            .define("Spawn Pride Variants Outside of Pride Month", false);
    public static final ModConfigSpec.IntValue BASE_RARE_CHANCE = BUILDER
            .comment("Determines the default value for the rare, golden moth variant")
            .comment("The integer used will be used like this: 1 out of <value> chance")
            .comment("Default value: 240")
            .defineInRange("Chance of a Moth Spawning as the Rare Variant", 240, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue BASE_RARE_CHANCE_MOTH_WEEK = BUILDER
            .comment("Determines the default value for the rare, golden moth variant during moth week (last week of July)")
            .comment("The integer used will be used like this: 1 out of <value> chance")
            .comment("Default value: 120")
            .defineInRange("Chance of a Moth Spawning as the Rare Variant During Moth Week", 120, 0, Integer.MAX_VALUE);

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {}
}
