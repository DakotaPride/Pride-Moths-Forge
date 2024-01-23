package net.dakotapride.pridemoths.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FuzzyCarpetBlock extends CarpetBlock {
    public FuzzyCarpetBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, @NotNull TooltipFlag options) {
        tooltip.add(Component.translatable("text.pridemoths.fuzzy_carpet.details").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
        boolean isHostile = entity instanceof Monster;
        if (isHostile) {
            ((Monster) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 9));
        }
    }
}
