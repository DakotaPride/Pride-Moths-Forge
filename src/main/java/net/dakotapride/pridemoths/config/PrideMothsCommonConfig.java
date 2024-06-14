package net.dakotapride.pridemoths.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PrideMothsCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH;
    public static final ForgeConfigSpec.ConfigValue<Integer> BASE_RARE_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> BASE_RARE_CHANCE_MOTH_WEEK;

    static {
        BUILDER.push("Configs for Happy Pride Moth! [Forge]");

        GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH = BUILDER
                .comment("Determines whether or not pride variants for moths spawn naturally outside of Pride Month")
                .comment("Default value: false")
                .define("Spawn Pride Variants Outside of Pride Month", false);
        BASE_RARE_CHANCE = BUILDER
                .comment("Determines the default value for the rare, golden moth variant")
                .comment("The integer used will be used like this: 1 out of <value> chance")
                .comment("Default value: 240")
                .define("Chance of a Moth Spawning as the Rare Variant", 240);
        BASE_RARE_CHANCE_MOTH_WEEK = BUILDER
                .comment("Determines the default value for the rare, golden moth variant during moth week (last week of July)")
                .comment("The integer used will be used like this: 1 out of <value> chance")
                .comment("Default value: 120")
                .define("Chance of a Moth Spawning as the Rare Variant During Moth Week", 120);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
