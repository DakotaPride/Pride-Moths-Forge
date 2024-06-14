package net.dakotapride.pridemoths.client.renderer;

import com.google.common.collect.Maps;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class MothRenderer extends GeoEntityRenderer<MothEntity> {
    public static final Map<MothVariation, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothVariation.class), (map) -> {
                map.put(MothVariation.DEFAULT, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/moth.png"));
                map.put(MothVariation.RARE, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/rare.png"));

                map.put(MothVariation.AGENDER, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/agender.png"));
                map.put(MothVariation.AROACE, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/aroace.png"));
                map.put(MothVariation.AROMANTIC, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/aromantic.png"));
                map.put(MothVariation.ASEXUAL, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/asexual.png"));
                map.put(MothVariation.BISEXUAL, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/bisexual.png"));
                map.put(MothVariation.DEMIBOY, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/demiboy.png"));
                map.put(MothVariation.DEMIGENDER, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/demigender.png"));
                map.put(MothVariation.DEMIGIRL, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/demigirl.png"));
                map.put(MothVariation.DEMIROMANTIC, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/demiromantic.png"));
                map.put(MothVariation.DEMISEXUAL, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/demisexual.png"));
                map.put(MothVariation.GAY, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/gay.png"));
                map.put(MothVariation.LESBIAN, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/lesbian.png"));
                map.put(MothVariation.LGBT, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/lgbt.png"));
                map.put(MothVariation.NON_BINARY, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/non_binary.png"));
                map.put(MothVariation.OMNISEXUAL, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/omnisexual.png"));
                map.put(MothVariation.PANSEXUAL, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/pansexual.png"));
                map.put(MothVariation.POLYAMOROUS, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/polyamorous.png"));
                map.put(MothVariation.POLYSEXUAL, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/polysexual.png"));
                map.put(MothVariation.TRANSGENDER, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/transgender.png"));
                map.put(MothVariation.ALLY, ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/pride/ally.png"));
            });

    public MothRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MothModel());
    }

    @Override
    public ResourceLocation getTextureLocation(MothEntity entity) {
        if (entity.isBaby()) {
            if (entity.getMothVariant() == MothVariation.RARE) {
                return ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/baby/rare.png");
            } else {
                return ResourceLocation.fromNamespaceAndPath("pridemoths", "textures/model/baby/moth.png");
            }
        }

        return LOCATION_BY_VARIANT.get(entity.getMothVariant());
    }
}