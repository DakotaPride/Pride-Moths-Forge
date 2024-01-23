package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MothModel extends AnimatedGeoModel<MothEntity> {

    @Override
    public ResourceLocation getModelResource(MothEntity entity) {
        if (entity.isBaby()) {
            return new ResourceLocation("pridemoths", "geo/baby_moth.geo.json");
        }

        return new ResourceLocation("pridemoths", "geo/moth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MothEntity entity) {
        if (entity.isBaby()) {
            if (entity.getMothVariant() == MothVariation.RARE) {
                return new ResourceLocation("pridemoths", "textures/model/baby/rare.png");
            } else {
                return new ResourceLocation("pridemoths", "textures/model/baby/moth.png");
            }
        }

        if (entity.getMothVariant() == MothVariation.RARE && !entity.isBaby()) {
            return new ResourceLocation("pridemoths", "textures/model/rare.png");
        } else {
            return new ResourceLocation("pridemoths", "textures/model/moth.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(MothEntity entity) {
        if (entity.isBaby()) {
            return new ResourceLocation("pridemoths", "animations/baby_moth.animation.json");
        }

        return new ResourceLocation("pridemoths", "animations/moth.animation.json");
    }
}