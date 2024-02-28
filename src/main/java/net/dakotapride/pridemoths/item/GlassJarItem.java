package net.dakotapride.pridemoths.item;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.MothEntity;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlassJarItem extends Item {
    public GlassJarItem(boolean i, Properties settings) {
        super(settings.stacksTo(16));
    }

    public GlassJarItem(Properties settings) {
        super(settings.stacksTo(1));
    }

    public static MothVariation getMothVariant(Item item) {
        MothVariation variant = null;
        ItemStack stack = item.getDefaultInstance();

        if (stack.is(PrideMothsMod.MOTH_JAR.get())) {
            variant = MothVariation.DEFAULT;
        } else if (stack.is(PrideMothsMod.RARE_MOTH_JAR.get())) {
            variant = MothVariation.RARE;
        } else if (stack.is(PrideMothsMod.AGENDER_MOTH_JAR.get())) {
            variant = MothVariation.AGENDER;
        } else if (stack.is(PrideMothsMod.AROACE_MOTH_JAR.get())) {
            variant = MothVariation.AROACE;
        } else if (stack.is(PrideMothsMod.AROMANTIC_MOTH_JAR.get())) {
            variant = MothVariation.AROMANTIC;
        } else if (stack.is(PrideMothsMod.ASEXUAL_MOTH_JAR.get())) {
            variant = MothVariation.ASEXUAL;
        } else if (stack.is(PrideMothsMod.BISEXUAL_MOTH_JAR.get())) {
            variant = MothVariation.BISEXUAL;
        } else if (stack.is(PrideMothsMod.DEMIBOY_MOTH_JAR.get())) {
            variant = MothVariation.DEMIBOY;
        } else if (stack.is(PrideMothsMod.DEMIGENDER_MOTH_JAR.get())) {
            variant = MothVariation.DEMIGENDER;
        } else if (stack.is(PrideMothsMod.DEMIGIRL_MOTH_JAR.get())) {
            variant = MothVariation.DEMIGIRL;
        } else if (stack.is(PrideMothsMod.DEMIROMANTIC_MOTH_JAR.get())) {
            variant = MothVariation.DEMIROMANTIC;
        } else if (stack.is(PrideMothsMod.DEMISEXUAL_MOTH_JAR.get())) {
            variant = MothVariation.DEMISEXUAL;
        } else if (stack.is(PrideMothsMod.GAY_MOTH_JAR.get())) {
            variant = MothVariation.GAY;
        } else if (stack.is(PrideMothsMod.LESBIAN_MOTH_JAR.get())) {
            variant = MothVariation.LESBIAN;
        } else if (stack.is(PrideMothsMod.LGBT_MOTH_JAR.get())) {
            variant = MothVariation.LGBT;
        } else if (stack.is(PrideMothsMod.NON_BINARY_MOTH_JAR.get())) {
            variant = MothVariation.NON_BINARY;
        } else if (stack.is(PrideMothsMod.OMNISEXUAL_MOTH_JAR.get())) {
            variant = MothVariation.OMNISEXUAL;
        } else if (stack.is(PrideMothsMod.PANSEXUAL_MOTH_JAR.get())) {
            variant = MothVariation.PANSEXUAL;
        } else if (stack.is(PrideMothsMod.POLYAMOROUS_MOTH_JAR.get())) {
            variant = MothVariation.POLYAMOROUS;
        } else if (stack.is(PrideMothsMod.POLYSEXUAL_MOTH_JAR.get())) {
            variant = MothVariation.POLYSEXUAL;
        } else if (stack.is(PrideMothsMod.TRANSGENDER_MOTH_JAR.get())) {
            variant = MothVariation.TRANSGENDER;
        } else if (stack.is(PrideMothsMod.ALLY_MOTH_JAR.get())) {
            variant = MothVariation.ALLY;
        }

        return variant;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        MothVariation variation = getMothVariant(context.getItemInHand().getItem());
        if (variation != null && context.getPlayer() != null && context.getPlayer().isCrouching()) {
            MothEntity moth = new MothEntity(PrideMothsMod.MOTH.get(), context.getLevel());

            BlockHitResult blockHitResult = BucketItem.getPlayerPOVHitResult(context.getLevel(), context.getPlayer(), ClipContext.Fluid.SOURCE_ONLY);
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos blockPos2 = blockPos.offset(direction.getNormal());

            moth.setPos(blockPos2.getX() + .5f, blockPos2.getY(), blockPos2.getZ() + .5f);
            moth.setMothVariant(variation);
            moth.fromJar = true;

            if (context.getItemInHand().hasCustomHoverName()) {
                moth.setCustomName(context.getItemInHand().getHoverName());
            }

            context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0f, 1.4f);
            context.getLevel().addFreshEntity(moth);

            if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(PrideMothsMod.GLASS_JAR.get()));
            }

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("text.pridemoths.jar.details").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));

        if (!stack.is(PrideMothsMod.GLASS_JAR.get())) {
            tooltip.add(Component.translatable("text.pridemoths.jar." + getMothVariant(stack.getItem()).getVariation()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
    }
}
