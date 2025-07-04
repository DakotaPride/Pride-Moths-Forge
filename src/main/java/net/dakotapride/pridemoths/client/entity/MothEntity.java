package net.dakotapride.pridemoths.client.entity;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.pride.IPrideMoths;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.dakotapride.pridemoths.config.PrideMothsCommonConfig;
import net.dakotapride.pridemoths.register.EntityTypeRegistrar;
import net.dakotapride.pridemoths.register.ItemsRegistrar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.EnumSet;
import java.util.List;

public class MothEntity extends Animal implements GeoEntity, FlyingAnimal, IPrideMoths {
    private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(MothEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public boolean fromJar = false;
    public static final List<MothVariation> PRIDE_VARIATIONS = List.of(
            MothVariation.TRANSGENDER, MothVariation.LGBT, MothVariation.NON_BINARY, MothVariation.AGENDER, MothVariation.ASEXUAL,
            MothVariation.GAY, MothVariation.LESBIAN, MothVariation.BISEXUAL, MothVariation.PANSEXUAL, MothVariation.POLYAMOROUS,
            MothVariation.POLYSEXUAL, MothVariation.OMNISEXUAL, MothVariation.AROMANTIC, MothVariation.AROACE, MothVariation.DEMIGIRL,
            MothVariation.DEMISEXUAL, MothVariation.DEMIGENDER, MothVariation.DEMIROMANTIC, MothVariation.GENDERFLUID, MothVariation.INTERSEX,
            MothVariation.XENOGENDER, MothVariation.GENDER_QUEER, MothVariation.GENDERFAE, MothVariation.GENDERFAUN, MothVariation.BIGENDER,
            MothVariation.PANGENDER);

    public MothEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
        this.noCulling = true;
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathType.COCOA, -1.0F);
        this.setPathfindingMalus(PathType.FENCE, -1.0F);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.25F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(PrideMothsMod.CAN_MOTH_EAT), false));
        this.targetSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(8, new MothWanderAroundGoal());
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader world) {
        return world.getBlockState(pos).isAir() ? 12.0F : world.getPathfindingCostFromLightLevels(pos);
    }

    public static MothVariation getPrideVariation(RandomSource random) {
        return PRIDE_VARIATIONS.get(random.nextInt(PRIDE_VARIATIONS.size()));
    }

    public static final List<MothVariation> ASEXUAL_VISIBILITY_DAY_VARIATIONS = List.of(
            MothVariation.ASEXUAL, MothVariation.DEMISEXUAL, MothVariation.AROACE);
    public static final List<MothVariation> AROMANTIC_VISIBILITY_DAY_VARIATIONS = List.of(
            MothVariation.AROMANTIC, MothVariation.DEMIROMANTIC, MothVariation.AROACE);

    public static MothVariation getAceVariation(RandomSource random) {
        return ASEXUAL_VISIBILITY_DAY_VARIATIONS.get(random.nextInt(ASEXUAL_VISIBILITY_DAY_VARIATIONS.size()));
    }

    public static MothVariation getAroVariation(RandomSource random) {
        return AROMANTIC_VISIBILITY_DAY_VARIATIONS.get(random.nextInt(AROMANTIC_VISIBILITY_DAY_VARIATIONS.size()));
    }

    public static final List<MothVariation> DEMIGENDER_VISIBILITY_DAY_VARIATIONS = List.of(
            MothVariation.DEMIBOY, MothVariation.DEMIGIRL, MothVariation.DEMIGENDER);

    public static MothVariation getDemigenderVariation(RandomSource random) {
        return DEMIGENDER_VISIBILITY_DAY_VARIATIONS.get(random.nextInt(DEMIGENDER_VISIBILITY_DAY_VARIATIONS.size()));
    }

    public static final List<MothVariation> TRANSGENDER_VISIBILITY_DAY_VARIATIONS = List.of(
            MothVariation.TRANSGENDER, MothVariation.NON_BINARY, MothVariation.AGENDER,
            MothVariation.DEMIBOY, MothVariation.DEMIGIRL, MothVariation.DEMIGENDER,
            MothVariation.GENDERFLUID, MothVariation.GENDER_QUEER, MothVariation.GENDERFAE, MothVariation.GENDERFAUN,
            MothVariation.BIGENDER, MothVariation.PANGENDER);

    public static MothVariation getTransgenderVariation(RandomSource random) {
        return TRANSGENDER_VISIBILITY_DAY_VARIATIONS.get(random.nextInt(TRANSGENDER_VISIBILITY_DAY_VARIATIONS.size()));
    }

    public static MothVariation getOtherVariation(RandomSource random) {
        int rarePatternChance = PrideMothsCommonConfig.base_rare_chance;
        if (IPrideMoths.isWorldMothWeek()) {
            rarePatternChance = PrideMothsCommonConfig.moth_week_rare_chance;
        }

        if (random.nextInt(rarePatternChance) == 1) {
            return MothVariation.RARE;
        } else {
            return MothVariation.DEFAULT;
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return EntityTypeRegistrar.MOTH.get().create(world);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return isFavouredFoodItem(stack);
    }

    public boolean isFavouredFoodItem(ItemStack stack) {
        return stack.getItem().getDefaultInstance().is(PrideMothsMod.CAN_MOTH_EAT);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(Pose pose) {
        return EntityDimensions.fixed(0.45F, 0.45F);
    }

    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(ItemsRegistrar.MOTH_FUZZ.get(), 1);
        }

    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        LocalDate date;
        date = LocalDate.now();
        int getLocalMonthFromUser = date.get(ChronoField.MONTH_OF_YEAR);

        if (IPrideMoths.isAgenderDayOfVisibility()) {
            setMothVariant(MothVariation.AGENDER);
        } else if (IPrideMoths.isBisexualDayOfVisibility()) {
            setMothVariant(MothVariation.BISEXUAL);
        } else if (IPrideMoths.isGayDayOfVisibility()) {
            setMothVariant(MothVariation.GAY);
        } else if (IPrideMoths.isLesbianDayOfVisibility()) {
            setMothVariant(MothVariation.LESBIAN);
        } else if (IPrideMoths.isPansexualDayOfVisibility()) {
            setMothVariant(MothVariation.PANSEXUAL);
        } else if (IPrideMoths.isOmnisexualDayOfVisibility()) {
            setMothVariant(MothVariation.OMNISEXUAL);
        } else if (IPrideMoths.isPolyamorousDayOfVisibility()) {
            setMothVariant(MothVariation.POLYAMOROUS);
        } else if (IPrideMoths.isPolysexualDayOfVisibility()) {
            setMothVariant(MothVariation.POLYSEXUAL);
        } else if (IPrideMoths.isIntersexDayOfVisibility()) {
            setMothVariant(MothVariation.INTERSEX);
        } else if (IPrideMoths.isXenogenderDayOfVisibility()) {
            setMothVariant(MothVariation.XENOGENDER);
        } else if (IPrideMoths.isGenderQueerDayOfVisibility()) {
            setMothVariant(MothVariation.GENDER_QUEER);
        } else if (IPrideMoths.isGenderfluidWeekOfVisibility()) {
            setMothVariant(MothVariation.GENDERFLUID);
        }

        else if (IPrideMoths.isTransgenderDayOfVisibility()) {
            setMothVariant(getTransgenderVariation(random));
        } else if (IPrideMoths.isAsexualDayOfVisibility()) {
            setMothVariant(getAceVariation(random));
        } else if (IPrideMoths.isAromanticDayOfVisibility()) {
            setMothVariant(getAroVariation(random));
        } else if (IPrideMoths.isDemigenderDayOfVisibility()) {
            setMothVariant(getDemigenderVariation(random));
        }

        else if (getLocalMonthFromUser == 6 || PrideMothsCommonConfig.pride_moths_outside_of_pride_moth) {
            setMothVariant(getPrideVariation(random));
        }

        else {
            setMothVariant(getOtherVariation(random));
        }

        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (isFood(itemstack)) {
            if (isFavouredFoodItem(itemstack)) {
                int i = this.getAge();
                if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                    this.usePlayerItem(player, hand, itemstack);
                    this.setInLove(player);
                    return InteractionResult.SUCCESS;
                }

                if (this.isBaby()) {
                    this.usePlayerItem(player, hand, itemstack);
                    this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                }

                if (this.level().isClientSide) {
                    return InteractionResult.CONSUME;
                }

            }
        }

        if (player.getItemInHand(hand).getItem() == ItemsRegistrar.GLASS_JAR.get() && !this.isBaby()) {
            ItemStack itemStack = getMothJarItemFromVariation();
            if (this.hasCustomName()) {
                itemStack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
            }

            if (!player.getAbilities().instabuild) {
                if (player.getItemInHand(hand).getCount() > 1) {
                    player.getItemInHand(hand).shrink(1);
                    if (!player.getInventory().add(itemStack)) {
                        player.drop(itemStack, true);
                    }
                } else {
                    player.setItemInHand(hand, itemStack);
                }
            } else {
                if (!player.getInventory().add(itemStack)) {
                    player.drop(itemStack, true);
                }
            }

            this.level().playSound(player, player.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.discard();
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return getMothJarItemFromVariation();
    }

    private @NotNull ItemStack getMothJarItemFromVariation() {
        Item item;

        switch (this.getMothVariant()) {
            case RARE -> item = ItemsRegistrar.RARE_MOTH_JAR.get();
            case AGENDER -> item = ItemsRegistrar.AGENDER_MOTH_JAR.get();
            case AROACE -> item = ItemsRegistrar.AROACE_MOTH_JAR.get();
            case AROMANTIC -> item = ItemsRegistrar.AROMANTIC_MOTH_JAR.get();
            case ASEXUAL -> item = ItemsRegistrar.ASEXUAL_MOTH_JAR.get();
            case BISEXUAL -> item = ItemsRegistrar.BISEXUAL_MOTH_JAR.get();
            case DEMIBOY -> item = ItemsRegistrar.DEMIBOY_MOTH_JAR.get();
            case DEMIGENDER -> item = ItemsRegistrar.DEMIGENDER_MOTH_JAR.get();
            case DEMIGIRL -> item = ItemsRegistrar.DEMIGIRL_MOTH_JAR.get();
            case DEMIROMANTIC -> item = ItemsRegistrar.DEMIROMANTIC_MOTH_JAR.get();
            case DEMISEXUAL -> item = ItemsRegistrar.DEMISEXUAL_MOTH_JAR.get();
            case GAY -> item = ItemsRegistrar.GAY_MOTH_JAR.get();
            case LESBIAN -> item = ItemsRegistrar.LESBIAN_MOTH_JAR.get();
            case LGBT -> item = ItemsRegistrar.LGBT_MOTH_JAR.get();
            case NON_BINARY -> item = ItemsRegistrar.NON_BINARY_MOTH_JAR.get();
            case OMNISEXUAL -> item = ItemsRegistrar.OMNISEXUAL_MOTH_JAR.get();
            case PANSEXUAL -> item = ItemsRegistrar.PANSEXUAL_MOTH_JAR.get();
            case POLYAMOROUS -> item = ItemsRegistrar.POLYAMOROUS_MOTH_JAR.get();
            case POLYSEXUAL -> item = ItemsRegistrar.POLYSEXUAL_MOTH_JAR.get();
            case TRANSGENDER -> item = ItemsRegistrar.TRANSGENDER_MOTH_JAR.get();
            case GENDERFLUID -> item = ItemsRegistrar.GENDERFLUID_MOTH_JAR.get();
            case INTERSEX -> item = ItemsRegistrar.INTERSEX_MOTH_JAR.get();
            case XENOGENDER -> item = ItemsRegistrar.XENOGENDER_MOTH_JAR.get();
            case GENDER_QUEER -> item = ItemsRegistrar.GENDER_QUEER_MOTH_JAR.get();
            case GENDERFAE -> item = ItemsRegistrar.GENDERFAE_MOTH_JAR.get();
            case GENDERFAUN -> item = ItemsRegistrar.GENDERFAUN_MOTH_JAR.get();
            case BIGENDER -> item = ItemsRegistrar.BIGENDER_MOTH_JAR.get();
            case PANGENDER -> item = ItemsRegistrar.PANGENDER_MOTH_JAR.get();
            default -> item = ItemsRegistrar.MOTH_JAR.get();
        }

        return new ItemStack(item);
    }

    public void setMothVariant(MothVariation type) {
        this.entityData.set(VARIANT, type.toString());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);

        builder.define(VARIANT, MothVariation.DEFAULT.toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        this.fromJar = tag.getBoolean("FromGlassJar");
        if (tag.contains("MothVariant")) {
            this.setMothVariant(MothVariation.valueOf(tag.getString("MothVariant")));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putBoolean("FromGlassJar", fromJar);
        tag.putString("MothVariant", this.getMothVariant().toString());
    }

    public MothVariation getMothVariant() {
        return MothVariation.valueOf(this.entityData.get(VARIANT));
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    private boolean queerNames() {
        String name = this.getName().getString();
        return name.equalsIgnoreCase("lgbt")
                || name.equalsIgnoreCase("lgbtq")
                || name.equalsIgnoreCase("lgbtqia")
                || name.equalsIgnoreCase("lgbtqia+");
    }

    private boolean nonBinaryNames() {
        String name = this.getName().getString();
        return name.equalsIgnoreCase("non-binary")
                || name.equalsIgnoreCase("non binary")
                || name.equalsIgnoreCase("non_binary")
                || name.equalsIgnoreCase("nonbinary")
                || name.equalsIgnoreCase("nyan-binary");
    }

    private boolean twoNames(String i0, String i1) {
        String name = this.getName().getString();
        return name.equalsIgnoreCase(i0) || name.equalsIgnoreCase(i1);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasCustomName() && !this.isBaby()) {
            if (this.getMothVariant() != MothVariation.NON_BINARY && nonBinaryNames()) {
                this.setMothVariant(MothVariation.NON_BINARY);
            } else if (this.getMothVariant() != MothVariation.TRANSGENDER && twoNames("trans", "transgender")) {
                this.setMothVariant(MothVariation.TRANSGENDER);
            } else if (this.getMothVariant() != MothVariation.LGBT && queerNames()) {
                this.setMothVariant(MothVariation.LGBT);
            } else if (this.getMothVariant() != MothVariation.GAY && twoNames("gay", "mlm")) {
                this.setMothVariant(MothVariation.GAY);
            } else if (this.getMothVariant() != MothVariation.LESBIAN && twoNames("lesbian", "wlw")) {
                this.setMothVariant(MothVariation.LESBIAN);
            } else if (this.getMothVariant() != MothVariation.AGENDER && this.getName().getString().equalsIgnoreCase("agender")) {
                this.setMothVariant(MothVariation.AGENDER);
            } else if (this.getMothVariant() != MothVariation.ASEXUAL && twoNames("asexual", "ace")) {
                this.setMothVariant(MothVariation.ASEXUAL);
            } else if (this.getMothVariant() != MothVariation.BISEXUAL && twoNames("bisexual", "bi")) {
                this.setMothVariant(MothVariation.BISEXUAL);
            } else if (this.getMothVariant() != MothVariation.PANSEXUAL && twoNames("pansexual", "pan")) {
                this.setMothVariant(MothVariation.PANSEXUAL);
            } else if (this.getMothVariant() != MothVariation.POLYAMOROUS && twoNames("polyamorous", "polygamous")) {
                this.setMothVariant(MothVariation.POLYAMOROUS);
            } else if (this.getMothVariant() != MothVariation.POLYSEXUAL && twoNames("polysexual", "poly")) {
                this.setMothVariant(MothVariation.POLYSEXUAL);
            } else if (this.getMothVariant() != MothVariation.OMNISEXUAL && twoNames("omnisexual", "omni")) {
                this.setMothVariant(MothVariation.OMNISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMISEXUAL && twoNames("demisexual", "demi")) {
                this.setMothVariant(MothVariation.DEMISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMIROMANTIC && twoNames("demiromantic", "demiro")) {
                this.setMothVariant(MothVariation.DEMIROMANTIC);
            } else if (this.getMothVariant() != MothVariation.DEMIBOY && this.getName().getString().equalsIgnoreCase("demiboy")) {
                this.setMothVariant(MothVariation.DEMIBOY);
            } else if (this.getMothVariant() != MothVariation.DEMIGIRL && this.getName().getString().equalsIgnoreCase("demigirl")) {
                this.setMothVariant(MothVariation.DEMIGIRL);
            } else if (this.getMothVariant() != MothVariation.DEMIGENDER && this.getName().getString().equalsIgnoreCase("demigender")) {
                this.setMothVariant(MothVariation.DEMIGENDER);
            } else if (this.getMothVariant() != MothVariation.AROACE && this.getName().getString().equalsIgnoreCase("aroace")) {
                this.setMothVariant(MothVariation.AROACE);
            } else if (this.getMothVariant() != MothVariation.GENDERFLUID && this.getName().getString().equalsIgnoreCase("genderfluid")) {
                this.setMothVariant(MothVariation.GENDERFLUID);
            } else if (this.getMothVariant() != MothVariation.INTERSEX && this.getName().getString().equalsIgnoreCase("intersex")) {
                this.setMothVariant(MothVariation.INTERSEX);
            } else if (this.getMothVariant() != MothVariation.XENOGENDER && this.getName().getString().equalsIgnoreCase("xenogender")) {
                this.setMothVariant(MothVariation.XENOGENDER);
            } else if (this.getMothVariant() != MothVariation.GENDER_QUEER && twoNames("gender_queer", "genderqueer")) {
                this.setMothVariant(MothVariation.GENDER_QUEER);
            } else if (this.getMothVariant() != MothVariation.GENDERFAE && this.getName().getString().equalsIgnoreCase("genderfae")) {
                this.setMothVariant(MothVariation.GENDERFAE);
            } else if (this.getMothVariant() != MothVariation.GENDERFAUN && this.getName().getString().equalsIgnoreCase("genderfaun")) {
                this.setMothVariant(MothVariation.GENDERFAUN);
            } else if (this.getMothVariant() != MothVariation.BIGENDER && this.getName().getString().equalsIgnoreCase("bigender")) {
                this.setMothVariant(MothVariation.BIGENDER);
            } else if (this.getMothVariant() != MothVariation.PANGENDER && this.getName().getString().equalsIgnoreCase("pangender")) {
                this.setMothVariant(MothVariation.PANGENDER);
            }

            if (this.getMothVariant() != MothVariation.ALLY && twoNames("ally", "straight")) {
                this.setMothVariant(MothVariation.ALLY);
            }

            if (this.getCustomName().getString().equalsIgnoreCase("super straight")) {
                this.kill();
            } else if (this.getCustomName().getString().equalsIgnoreCase("super_straight")) {
                this.kill();
            } else if (this.getCustomName().getString().equalsIgnoreCase("superstraight")) {
                this.kill();
            } else if (this.getCustomName().getString().equalsIgnoreCase("super-straight")) {
                this.kill();
            }
        }

    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world);
        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanFloat(false);
        birdNavigation.setCanPassDoors(false);

        return birdNavigation;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {

        event.getController().setAnimation(RawAnimation.begin().then("animation.moth.idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.AXOLOTL_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.AXOLOTL_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // Fake target
    @Override
    public boolean canAttackType(EntityType<?> type) {
        return type == EntityType.PLAYER;
    }

    class MothWanderAroundGoal extends Goal {
        MothWanderAroundGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return MothEntity.this.navigation.isDone() && MothEntity.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return MothEntity.this.navigation.isInProgress();
        }

        @Override
        public void start() {
            Vec3 vec3d = this.getRandomLocation();
            if (vec3d != null) {
                MothEntity.this.navigation.moveTo(MothEntity.this.navigation.createPath(BlockPos.containing(vec3d), 1), 1.0);
            }
        }

        @Nullable
        private Vec3 getRandomLocation() {
            Vec3 vec3d2 = MothEntity.this.getViewVector(0.35F);

            Vec3 vec3d3 = HoverRandomPos.getPos(MothEntity.this, 8, 7, vec3d2.x, vec3d2.z, (float) (Math.PI / 2), 3, 1);
            return vec3d3 != null ? vec3d3 : AirAndWaterRandomPos.getPos(MothEntity.this, 8, 4, -2, vec3d2.x, vec3d2.z, (float) (Math.PI / 2));
        }
    }

}
