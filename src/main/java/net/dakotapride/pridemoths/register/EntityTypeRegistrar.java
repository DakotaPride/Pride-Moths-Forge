package net.dakotapride.pridemoths.register;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntityTypeRegistrar {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, PrideMothsMod.ID);

    public static DeferredHolder<EntityType<?>, EntityType<MothEntity>> MOTH = ENTITY_TYPES.register(
            "moth", () -> EntityType.Builder.of(MothEntity::new, MobCategory.CREATURE)
                    .sized(0.45F, 0.45F).build(keyOf("moth")));

    private static ResourceKey<EntityType<?>> keyOf(String id) {
        return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, id));
    }

    public static void yep(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
