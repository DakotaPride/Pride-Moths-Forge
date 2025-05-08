package net.dakotapride.pridemoths.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class FuzzyCarpetBlock extends CarpetBlock {
    public static final MapCodec<FuzzyCarpetBlock> CODEC = simpleCodec(FuzzyCarpetBlock::new);
    public FuzzyCarpetBlock(Properties settings) {
        super(settings);
    }
    @Override
    public @NotNull MapCodec<? extends FuzzyCarpetBlock> codec() {
        return CODEC;
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity, InsideBlockEffectApplier insideBlockEffectApplier) {
        boolean isHostile = entity instanceof Monster;
        if (isHostile) {
            ((Monster) entity).addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 600, 9));
        }
    }

    public static class FuzzyCarpetBlockItem extends BlockItem {
        public FuzzyCarpetBlockItem(Block block, Properties properties) {
            super(block, properties);
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> textConsumer, TooltipFlag flag) {
            textConsumer.accept(Component.translatable("text.pridemoths.fuzzy_carpet.details").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
    }
}
