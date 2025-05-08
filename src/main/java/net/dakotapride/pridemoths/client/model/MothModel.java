package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class MothModel extends GeoModel<MothEntity> {

    @Override
    public ResourceLocation getModelResource(MothEntity entity, @Nullable GeoRenderer<MothEntity> renderer) {
        if (entity.isBaby()) {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "geo/baby_moth.geo.json");
        }

        return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "geo/moth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MothEntity entity, @Nullable GeoRenderer<MothEntity> renderer) {
        if (entity.isBaby()) {
            if (entity.getMothVariant() == MothVariation.RARE) {
                return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/baby/rare.png");
            } else {
                return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/baby/moth.png");
            }
        }

        if (entity.getMothVariant() == MothVariation.RARE && !entity.isBaby()) {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/rare.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/moth.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(MothEntity entity) {
        if (entity.isBaby()) {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "animations/baby_moth.animation.json");
        }

        return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "animations/moth.animation.json");
    }
}