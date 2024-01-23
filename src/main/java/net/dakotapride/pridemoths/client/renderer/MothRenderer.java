package net.dakotapride.pridemoths.client.renderer;

import com.google.common.collect.Maps;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class MothRenderer extends GeoEntityRenderer<MothEntity> {
    public static final Map<MothVariation, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothVariation.class), (map) -> {
                map.put(MothVariation.DEFAULT, new ResourceLocation("pridemoths", "textures/model/moth.png"));
                map.put(MothVariation.RARE, new ResourceLocation("pridemoths", "textures/model/rare.png"));

                map.put(MothVariation.AGENDER, new ResourceLocation("pridemoths", "textures/model/pride/agender.png"));
                map.put(MothVariation.AROACE, new ResourceLocation("pridemoths", "textures/model/pride/aroace.png"));
                map.put(MothVariation.AROMANTIC, new ResourceLocation("pridemoths", "textures/model/pride/aromantic.png"));
                map.put(MothVariation.ASEXUAL, new ResourceLocation("pridemoths", "textures/model/pride/asexual.png"));
                map.put(MothVariation.BISEXUAL, new ResourceLocation("pridemoths", "textures/model/pride/bisexual.png"));
                map.put(MothVariation.DEMIBOY, new ResourceLocation("pridemoths", "textures/model/pride/demiboy.png"));
                map.put(MothVariation.DEMIGENDER, new ResourceLocation("pridemoths", "textures/model/pride/demigender.png"));
                map.put(MothVariation.DEMIGIRL, new ResourceLocation("pridemoths", "textures/model/pride/demigirl.png"));
                map.put(MothVariation.DEMIROMANTIC, new ResourceLocation("pridemoths", "textures/model/pride/demiromantic.png"));
                map.put(MothVariation.DEMISEXUAL, new ResourceLocation("pridemoths", "textures/model/pride/demisexual.png"));
                map.put(MothVariation.GAY, new ResourceLocation("pridemoths", "textures/model/pride/gay.png"));
                map.put(MothVariation.LESBIAN, new ResourceLocation("pridemoths", "textures/model/pride/lesbian.png"));
                map.put(MothVariation.LGBT, new ResourceLocation("pridemoths", "textures/model/pride/lgbt.png"));
                map.put(MothVariation.NON_BINARY, new ResourceLocation("pridemoths", "textures/model/pride/non_binary.png"));
                map.put(MothVariation.OMNISEXUAL, new ResourceLocation("pridemoths", "textures/model/pride/omnisexual.png"));
                map.put(MothVariation.PANSEXUAL, new ResourceLocation("pridemoths", "textures/model/pride/pansexual.png"));
                map.put(MothVariation.POLYAMOROUS, new ResourceLocation("pridemoths", "textures/model/pride/polyamorous.png"));
                map.put(MothVariation.POLYSEXUAL, new ResourceLocation("pridemoths", "textures/model/pride/polysexual.png"));
                map.put(MothVariation.TRANSGENDER, new ResourceLocation("pridemoths", "textures/model/pride/transgender.png"));
                map.put(MothVariation.ALLY, new ResourceLocation("pridemoths", "textures/model/pride/ally.png"));
            });

    public MothRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MothModel());
    }

    @Override
    public ResourceLocation getTextureLocation(MothEntity entity) {
        if (entity.isBaby()) {
            if (entity.getMothVariant() == MothVariation.RARE) {
                return new ResourceLocation("pridemoths", "textures/model/baby/rare.png");
            } else {
                return new ResourceLocation("pridemoths", "textures/model/baby/moth.png");
            }
        }

        return LOCATION_BY_VARIANT.get(entity.getMothVariant());
    }
}