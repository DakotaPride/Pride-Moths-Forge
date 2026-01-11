package net.dakotapride.pridemoths.client.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.model.MothModel;
import net.dakotapride.pridemoths.client.model.MothRenderState;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class MothRenderer extends MobRenderer<MothEntity, MothRenderState, MothModel> {
    public static final Map<MothVariation, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothVariation.class), (map) -> {
                map.put(MothVariation.DEFAULT, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/moth.png"));
                map.put(MothVariation.RARE, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/rare.png"));

                map.put(MothVariation.AGENDER, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/agender.png"));
                map.put(MothVariation.AROACE, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/aroace.png"));
                map.put(MothVariation.AROMANTIC, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/aromantic.png"));
                map.put(MothVariation.ASEXUAL, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/asexual.png"));
                map.put(MothVariation.BISEXUAL, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/bisexual.png"));
                map.put(MothVariation.DEMIBOY, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demiboy.png"));
                map.put(MothVariation.DEMIGENDER, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demigender.png"));
                map.put(MothVariation.DEMIGIRL, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demigirl.png"));
                map.put(MothVariation.DEMIROMANTIC, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demiromantic.png"));
                map.put(MothVariation.DEMISEXUAL, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/demisexual.png"));
                map.put(MothVariation.GAY, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/gay.png"));
                map.put(MothVariation.LESBIAN, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/lesbian.png"));
                map.put(MothVariation.LGBT, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/lgbt.png"));
                map.put(MothVariation.NON_BINARY, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/non_binary.png"));
                map.put(MothVariation.OMNISEXUAL, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/omnisexual.png"));
                map.put(MothVariation.PANSEXUAL, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/pansexual.png"));
                map.put(MothVariation.POLYAMOROUS, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/polyamorous.png"));
                map.put(MothVariation.POLYSEXUAL, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/polysexual.png"));
                map.put(MothVariation.TRANSGENDER, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/transgender.png"));
                map.put(MothVariation.GENDERFLUID, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/genderfluid.png"));
                map.put(MothVariation.INTERSEX, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/intersex.png"));
                map.put(MothVariation.XENOGENDER, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/xenogender.png"));
                map.put(MothVariation.GENDER_QUEER, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/gender_queer.png"));
                map.put(MothVariation.GENDERFAE, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/genderfae.png"));
                map.put(MothVariation.GENDERFAUN, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/genderfaun.png"));
                map.put(MothVariation.BIGENDER, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/bigender.png"));
                map.put(MothVariation.PANGENDER, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/pangender.png"));
                map.put(MothVariation.ALLY, Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/pride/ally.png"));
            });

    public MothRenderer(EntityRendererProvider.Context context) {
        super(context, new MothModel(context.bakeLayer(MothModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public Identifier getTextureLocation(MothRenderState entity) {
        //return LOCATION_BY_VARIANT.get(entity.variant);

//        if (entity.isBaby) {
//            if (entity.variant == MothVariation.RARE) {
//                return Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/baby/rare.png");
//            } else {
//                return Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/baby/moth.png");
//            }
//        }

        if (entity.variant == MothVariation.RARE) {
            return Identifier.fromNamespaceAndPath(PrideMothsMod.ID, "textures/model/rare.png");
        } else return LOCATION_BY_VARIANT.get(entity.variant);
    }

    @Override
    public void submit(MothRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if(renderState.isBaby) {
            poseStack.scale(0.45f, 0.45f, 0.45f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }

        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
    }

    @Override
    public MothRenderState createRenderState() {
        return new MothRenderState();
    }

    @Override
    public void extractRenderState(MothEntity entity, MothRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.idleAnimationState.copyFrom(entity.idleAnimationState);
        reusedState.variant = entity.getMothVariant();
    }
}