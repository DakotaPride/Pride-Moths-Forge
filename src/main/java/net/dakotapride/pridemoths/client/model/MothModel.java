package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MothModel extends GeoModel<MothEntity> {

    @Override
    public ResourceLocation getModelResource(MothEntity entity) {
        if (entity.isBaby()) {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.MOD_ID, "geo/baby_moth.geo.json");
        }

        return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.MOD_ID, "geo/moth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MothEntity entity) {
        if (entity.isBaby()) {
            if (entity.getMothVariant() == MothVariation.RARE) {
                return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.MOD_ID, "textures/model/baby/rare.png");
            } else {
                return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.MOD_ID, "textures/model/baby/moth.png");
            }
        }

        if (entity.getMothVariant() == MothVariation.RARE && !entity.isBaby()) {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.MOD_ID, "textures/model/rare.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.MOD_ID, "textures/model/moth.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(MothEntity entity) {
        if (entity.isBaby()) {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.MOD_ID, "animations/baby_moth.animation.json");
        }

        return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.MOD_ID, "animations/moth.animation.json");
    }
}