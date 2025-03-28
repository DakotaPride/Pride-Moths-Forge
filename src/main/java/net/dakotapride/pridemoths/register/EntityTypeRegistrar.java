package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeRegistrar {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, PrideMothsMod.MOD_ID);

    public static RegistryObject<EntityType<? extends MothEntity>> MOTH =
            ENTITY_TYPES.register("moth",
                    () -> EntityType.Builder.of(MothEntity::new, MobCategory.CREATURE)
                            .sized(0.45f, 0.45f)
                            .build(new ResourceLocation(PrideMothsMod.MOD_ID, "moth").toString()));

    public static void yep(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
