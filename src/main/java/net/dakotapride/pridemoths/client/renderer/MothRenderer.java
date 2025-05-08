package net.dakotapride.pridemoths.client.renderer;

import com.google.common.collect.Maps;
import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.Map;

public class MothRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<MothEntity, R> {
    public static final Map<MothVariation, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothVariation.class), (map) -> {
                map.put(MothVariation.DEFAULT, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/moth.png"));
                map.put(MothVariation.RARE, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/rare.png"));

                map.put(MothVariation.AGENDER, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/agender.png"));
                map.put(MothVariation.AROACE, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/aroace.png"));
                map.put(MothVariation.AROMANTIC, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/aromantic.png"));
                map.put(MothVariation.ASEXUAL, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/asexual.png"));
                map.put(MothVariation.BISEXUAL, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/bisexual.png"));
                map.put(MothVariation.DEMIBOY, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demiboy.png"));
                map.put(MothVariation.DEMIGENDER, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demigender.png"));
                map.put(MothVariation.DEMIGIRL, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demigirl.png"));
                map.put(MothVariation.DEMIROMANTIC, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demiromantic.png"));
                map.put(MothVariation.DEMISEXUAL, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demisexual.png"));
                map.put(MothVariation.GAY, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/gay.png"));
                map.put(MothVariation.LESBIAN, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/lesbian.png"));
                map.put(MothVariation.LGBT, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/lgbt.png"));
                map.put(MothVariation.NON_BINARY, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/non_binary.png"));
                map.put(MothVariation.OMNISEXUAL, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/omnisexual.png"));
                map.put(MothVariation.PANSEXUAL, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/pansexual.png"));
                map.put(MothVariation.POLYAMOROUS, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/polyamorous.png"));
                map.put(MothVariation.POLYSEXUAL, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/polysexual.png"));
                map.put(MothVariation.TRANSGENDER, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/transgender.png"));
                map.put(MothVariation.GENDERFLUID, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/genderfluid.png"));
                map.put(MothVariation.INTERSEX, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/intersex.png"));
                map.put(MothVariation.XENOGENDER, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/xenogender.png"));
                map.put(MothVariation.GENDER_QUEER, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/gender_queer.png"));
                map.put(MothVariation.GENDERFAE, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/genderfae.png"));
                map.put(MothVariation.GENDERFAUN, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/genderfaun.png"));
                map.put(MothVariation.BIGENDER, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/bigender.png"));
                map.put(MothVariation.PANGENDER, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/pangender.png"));
                map.put(MothVariation.ALLY, ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/ally.png"));
            });

    public MothRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MothModel());
    }

    MothVariation variation;
    boolean baby;

    @Override
    public void addRenderData(MothEntity animatable, Void relatedObject, R renderState) {
        super.addRenderData(animatable, relatedObject, renderState);
        variation = animatable.getMothVariant();
        baby = animatable.isBaby();
    }

    @Override
    public ResourceLocation getTextureLocation(R renderState) {
        if (baby) {
            if (variation == MothVariation.RARE)
                return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/baby/rare.png");
            return ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/baby/moth.png");
        }

        return LOCATION_BY_VARIANT.get(variation);
    }
}