package net.dakotapride.pridemoths.block;

import com.mojang.serialization.MapCodec;
import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.item.GlassJarItem;
import net.dakotapride.pridemoths.register.BlockEntityTypeRegistrar;
import net.dakotapride.pridemoths.register.DataComponentsRegistrar;
import net.dakotapride.pridemoths.register.ItemsRegistrar;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;

public class MothEnclosureBlock extends BaseEntityBlock implements EntityBlock {
    public static final MapCodec<MothEnclosureBlock> CODEC = simpleCodec(MothEnclosureBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty FUZZ_LEVEL = PrideMothsMod.FUZZ_LEVEL;
    public static final List<BooleanProperty> SLOT_OCCUPIED_PROPERTIES = List.of(
            BlockStateProperties.SLOT_0_OCCUPIED,
            BlockStateProperties.SLOT_1_OCCUPIED,
            BlockStateProperties.SLOT_2_OCCUPIED
    );

    public MothEnclosureBlock(Properties settings) {
        super(settings);
        BlockState blockState = this.stateDefinition.getOwner().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(FUZZ_LEVEL, 0);

        for (BooleanProperty booleanProperty : SLOT_OCCUPIED_PROPERTIES) {
            blockState = blockState.setValue(booleanProperty, Boolean.FALSE);
        }

        this.registerDefaultState(blockState);
    }

    public static class MothEnclosureBlockItem extends BlockItem {
        public MothEnclosureBlockItem(Block block, Properties settings) {
            super(block, settings.setId(PrideMothsMod.keyOfItem("moth_enclosure")).overrideDescription("block.pridemoths.moth_enclosure"));
        }

        @Override
        public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> textConsumer, TooltipFlag flag) {
            for (ItemStack stack : itemStack.getOrDefault(DataComponentsRegistrar.MOTH_CONTAINER, ItemContainerContents.EMPTY).nonEmptyItems()) {
                if (stack.is(PrideMothsMod.MOTH_JARS) && stack.getItem() instanceof GlassJarItem jarItem) {
                    textConsumer.accept(Component.translatable("container.mothEnclosure.itemCount." +
                            GlassJarItem.getMothVariant(jarItem).getVariation()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                }
            }
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return SLOT_OCCUPIED_PROPERTIES != null;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.8F) {
            if (checkIfOnlyOneSlotIsFilled(state)) {
                if (state.is(PrideMothsMod.MOTH_ENCLOSURES, statex -> statex.hasProperty(FUZZ_LEVEL))) {
                    int i = MothEnclosureBlockEntity.getMothFuzzLevel(state);
                    if (i < 1) {
                        int j = world.random.nextInt(100) == 0 ? 2 : 1;
                        if (i + j > 1) {
                            j--;
                        }

                        world.setBlockAndUpdate(pos, state.setValue(FUZZ_LEVEL, i + j));
                    }
                }
            } else if (checkIfOnlyTwoSlotsAreFilled(state)) {
                if (state.is(PrideMothsMod.MOTH_ENCLOSURES, statex -> statex.hasProperty(FUZZ_LEVEL))) {
                    int i = MothEnclosureBlockEntity.getMothFuzzLevel(state);
                    if (i < 2) {
                        int j = world.random.nextInt(100) == 0 ? 2 : 1;
                        if (i + j > 2) {
                            j--;
                        }

                        world.setBlockAndUpdate(pos, state.setValue(FUZZ_LEVEL, i + j));
                    }
                }
            } else if (checkIfAllSlotsAreFilled(state)) {
                if (state.is(PrideMothsMod.MOTH_ENCLOSURES, statex -> statex.hasProperty(FUZZ_LEVEL))) {
                    int i = MothEnclosureBlockEntity.getMothFuzzLevel(state);
                    if (i < 3) {
                        int j = world.random.nextInt(100) == 0 ? 2 : 1;
                        if (i + j > 3) {
                            j--;
                        }

                        world.setBlockAndUpdate(pos, state.setValue(FUZZ_LEVEL, i + j));
                    }
                }
            }
        }

        super.randomTick(state, world, pos, random);
    }

    private static boolean checkIfOnlyOneSlotIsFilled(BlockState state) {
        if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return true;
        if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return true;
        return !state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED);
    }

    private static boolean checkIfOnlyTwoSlotsAreFilled(BlockState state) {
        if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return true;
        if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return true;
        return state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED);
    }

    private static boolean checkIfAllSlotsAreFilled(BlockState state) {
        return state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED);
    }

    public static void dropMothFuzz(Level world, BlockPos pos, BlockState state) {
        if (state.getValue(FUZZ_LEVEL) == 1) {
            popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 1));
        }
        if (state.getValue(FUZZ_LEVEL) == 2) {
            if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 1));
            if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 1));
            if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 1));

            if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 2));
            if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 2));
            if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 2));

            if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 2));
        }
        if (state.getValue(FUZZ_LEVEL) == 3) {
            if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 1));
            if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 1));
            if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 1));

            if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 2));
            if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 2));
            if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 2));

            if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
                popResource(world, pos, new ItemStack(ItemsRegistrar.MOTH_FUZZ.get(), 3));
        }
    }

    public void takeMothFuzz(Level world, BlockState state, BlockPos pos) {
        world.setBlock(pos, state.setValue(FUZZ_LEVEL, 0), Block.UPDATE_ALL);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof MothEnclosureBlockEntity mothEnclosureBlockEntity) {
            if (!stack.is(PrideMothsMod.MOTH_JARS)) {
                int i = state.getValue(FUZZ_LEVEL);
                boolean bl = false;
                if (i >= 1) {
                    if (stack.getItem() instanceof ShearsItem) {
                        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                        dropMothFuzz(world, pos, state);
                        stack.hurtAndBreak(1, player, hand.asEquipmentSlot());
                        bl = true;
                        world.gameEvent(player, GameEvent.SHEAR, pos);
                    }
                }

                if (bl) {
                    this.takeMothFuzz(world, state, pos);

                    return InteractionResult.SUCCESS;
                }

                return InteractionResult.TRY_WITH_EMPTY_HAND;
            } else {
                OptionalInt optionalInt = this.getSlotForHitPos(hit, state);
                if (optionalInt.isEmpty()) {
                    return InteractionResult.PASS;
                } else if (state.getValue(SLOT_OCCUPIED_PROPERTIES.get(optionalInt.getAsInt()))) {
                    return InteractionResult.TRY_WITH_EMPTY_HAND;
                } else {
                    tryAddGlassJarWithMothInside(world, pos, player, mothEnclosureBlockEntity, stack, optionalInt.getAsInt());
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof MothEnclosureBlockEntity mothEnclosureBlockEntity) {
            OptionalInt optionalInt = this.getSlotForHitPos(hit, state);
            if (optionalInt.isEmpty()) {
                return InteractionResult.PASS;
            } else if (!(Boolean)state.getValue(SLOT_OCCUPIED_PROPERTIES.get(optionalInt.getAsInt()))) {
                return InteractionResult.CONSUME;
            } else {
                if (!player.getMainHandItem().is(Items.SHEARS)) {
                    tryRemoveGlassJarWithMothInside(world, pos, player, mothEnclosureBlockEntity, optionalInt.getAsInt());
                }
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    private OptionalInt getSlotForHitPos(BlockHitResult hit, BlockState state) {
        return getHitPos(hit, state.getValue(HorizontalDirectionalBlock.FACING)).map(hitPos -> {
            //int i = 0;
            int j = getColumn(hitPos.x);
            return OptionalInt.of(j);
        }).orElseGet(OptionalInt::empty);
    }

    private static Optional<Vec2> getHitPos(BlockHitResult hit, Direction facing) {
        Direction direction = hit.getDirection();
        if (facing != direction) {
            return Optional.empty();
        } else {
            BlockPos blockpos = hit.getBlockPos().relative(direction);
            Vec3 vec3 = hit.getLocation().subtract(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            double d = vec3.x();
            double e = vec3.y();
            double f = vec3.z();

            return switch (direction) {
                case NORTH -> Optional.of(new Vec2((float)(1.0 - d), (float)e));
                case SOUTH -> Optional.of(new Vec2((float)d, (float)e));
                case WEST -> Optional.of(new Vec2((float)f, (float)e));
                case EAST -> Optional.of(new Vec2((float)(1.0 - f), (float)e));
                case DOWN, UP -> Optional.empty();
            };
        }
    }

    private static int getColumn(float x) {
        if (x < 0.375F) {
            return 0;
        } else {
            return x < 0.6875F ? 1 : 2;
        }
    }

    private static void tryAddGlassJarWithMothInside(Level world, BlockPos pos, Player player, MothEnclosureBlockEntity blockEntity, ItemStack stack, int slot) {
        if (!world.isClientSide()) {
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            SoundEvent soundEvent = SoundEvents.BOTTLE_EMPTY;
            blockEntity.setItem(slot, stack.consumeAndReturn(1, player));
            world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    private static void tryRemoveGlassJarWithMothInside(Level world, BlockPos pos, Player player, MothEnclosureBlockEntity blockEntity, int slot) {
        if (!world.isClientSide()) {
            ItemStack itemStack = blockEntity.removeItem(slot, 1);
            SoundEvent soundEvent = SoundEvents.BOTTLE_FILL;
            world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.getInventory().add(itemStack)) {
                player.drop(itemStack, false);
            }

            world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide() && world instanceof ServerLevel serverLevel
                //&& player.isCreative()
                && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)
                && world.getBlockEntity(pos) instanceof MothEnclosureBlockEntity mothEnclosureBlockEntity) {
            ItemStack itemStack = new ItemStack(this);
            //int i = state.get(FUZZ_LEVEL);
            boolean slot0 = state.getValue(SLOT_OCCUPIED_PROPERTIES.getFirst());
            boolean slot1 = state.getValue(SLOT_OCCUPIED_PROPERTIES.get(1));
            boolean slot2 = state.getValue(SLOT_OCCUPIED_PROPERTIES.get(2));
            if (slot0 || slot1 || slot2) {
                itemStack.applyComponents(mothEnclosureBlockEntity.collectComponents());
                world.getBlockEntity(pos, BlockEntityTypeRegistrar.MOTH_ENCLOSURE.get()).ifPresent(blockEntity -> blockEntity.applyComponentsFromItemStack(itemStack));
                itemStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY
                        .with(SLOT_OCCUPIED_PROPERTIES.getFirst(), slot0)
                        .with(SLOT_OCCUPIED_PROPERTIES.get(1), slot1)
                        .with(SLOT_OCCUPIED_PROPERTIES.get(2), slot2));
                if (mothEnclosureBlockEntity.hasCustomName()) {
                    itemStack.set(DataComponents.CUSTOM_NAME, mothEnclosureBlockEntity.getCustomName());
                }
            }

            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
            itemEntity.setDefaultPickUpDelay();
            world.addFreshEntity(itemEntity);
        }

        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        Containers.updateNeighboursAfterDestroy(state, world, pos);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        ItemStack itemStack = super.getCloneItemStack(level, pos, state, includeData, player);
        level.getBlockEntity(pos, BlockEntityTypeRegistrar.MOTH_ENCLOSURE.get()).ifPresent(blockEntity -> blockEntity.applyComponentsFromItemStack(itemStack));
        return itemStack;
    }

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MothEnclosureBlockEntity(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FUZZ_LEVEL);
        SLOT_OCCUPIED_PROPERTIES.forEach(builder::add);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public static int getRedstoneAnalogOutput(BlockState state) {
        if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return 1;
        if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return 1;
        if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return 1;

        if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return 2;
        if (!state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return 2;
        if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && !state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return 2;

        if (state.getValue(BlockStateProperties.SLOT_0_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_1_OCCUPIED) && state.getValue(BlockStateProperties.SLOT_2_OCCUPIED))
            return 3;

        return 0;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos blockPos, Direction direction) {
        return getRedstoneAnalogOutput(state);
    }
}
