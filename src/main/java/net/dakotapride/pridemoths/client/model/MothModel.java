package net.dakotapride.pridemoths.client.model;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class MothModel extends GeoModel<MothEntity> {

    MothVariation variation;
    boolean baby;

    @Override
    public void addAdditionalStateData(MothEntity animatable, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, renderState);
        variation = animatable.getMothVariant();
        baby = animatable.isBaby();
    }

    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        if (baby)
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "baby_moth");
        return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "moth");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        if (baby) {
            if (variation == MothVariation.RARE) {
                return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/baby/rare.png");
            } else {
                return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/baby/moth.png");
            }
        }

        if (variation == MothVariation.RARE) {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/rare.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/moth.png");
        }
        //return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/moth.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MothEntity entity) {
        if (entity.isBaby()) {
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "baby_moth");
        }

        return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "moth");
    }
}