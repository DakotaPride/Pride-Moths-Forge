package net.dakotapride.pridemoths.client.entity;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.client.entity.pride.IPrideMoths;
import net.dakotapride.pridemoths.client.entity.pride.MothVariation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

public class MothEntity extends Animal implements GeoEntity, FlyingAnimal, IPrideMoths {
    private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(MothEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public boolean fromJar = false;
    public static final List<MothVariation> PRIDE_VARIATIONS = List.of(
            MothVariation.TRANSGENDER, MothVariation.LGBT, MothVariation.NON_BINARY, MothVariation.AGENDER, MothVariation.ASEXUAL,
            MothVariation.GAY, MothVariation.LESBIAN, MothVariation.BISEXUAL, MothVariation.PANSEXUAL, MothVariation.POLYAMOROUS,
            MothVariation.POLYSEXUAL, MothVariation.OMNISEXUAL, MothVariation.AROMANTIC, MothVariation.AROACE, MothVariation.DEMIGIRL,
            MothVariation.DEMISEXUAL, MothVariation.DEMIGENDER, MothVariation.DEMIROMANTIC);

    public MothEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
        this.noCulling = true;
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.25F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MothFlyGoal(this, 1.0));
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new TravelToLightSourceGoal(this, 32));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(PrideMothsMod.CAN_MOTH_EAT), false));
        this.targetSelector.addGoal(1, new BreedGoal(this, 1.0));
    }

    public static MothVariation getPrideVariation(RandomSource random) {
        return PRIDE_VARIATIONS.get(random.nextInt(PRIDE_VARIATIONS.size()));
    }

    public static MothVariation getOtherVariation(RandomSource random) {
        int rarePatternChance = 240;
        if (IPrideMoths.isWorldMothWeek()) {
            rarePatternChance = 120;
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
        return PrideMothsMod.MOTH.get().create(world);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return isFavouredFoodItem(stack);
    }

    public boolean isFavouredFoodItem(ItemStack stack) {
        return stack.getItem().getDefaultInstance().is(PrideMothsMod.CAN_MOTH_EAT);
    }

    public boolean dislikesFoodItem(ItemStack stack) {
        return stack.getItem().getDefaultInstance().is(PrideMothsMod.DAMAGES_MOTH_UPON_CONSUMPTION);
    }

    public boolean isAllergicToFoodItem(ItemStack stack) {
        return stack.getItem().getDefaultInstance().is(PrideMothsMod.KILLS_MOTH_UPON_CONSUMPTION);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.fixed(0.3F, 0.3F);
    }

    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(PrideMothsMod.MOTH_FUZZ.get(), 1);
        }

    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
                                     @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        LocalDate date;
        date = LocalDate.now();
        int getLocalMonthFromUser = date.get(ChronoField.MONTH_OF_YEAR);

        if (getLocalMonthFromUser == 6) {
            setMothVariant(getPrideVariation(random));
        } else {
            setMothVariant(getOtherVariation(random));
        }

        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (isFood(getUseItem()) && !this.isBaby()) {
            return super.mobInteract(player, hand);
        } else if (dislikesFoodItem(getUseItem())) {
            this.hurt(player.damageSources().generic(), 1.0F);
        } else if (isAllergicToFoodItem(getUseItem()) || (getUseItem().getItem().getFoodProperties(getUseItem(), player) != null && getUseItem().getItem().getFoodProperties(getUseItem(), player).isMeat())) {
            this.kill();
        }

        if (player.getItemInHand(hand).getItem() == PrideMothsMod.GLASS_JAR.get() && !this.isBaby()) {
            Item item;

            switch (this.getMothVariant()) {
                case RARE -> item = PrideMothsMod.RARE_MOTH_JAR.get();
                case AGENDER -> item = PrideMothsMod.AGENDER_MOTH_JAR.get();
                case AROACE -> item = PrideMothsMod.AROACE_MOTH_JAR.get();
                case AROMANTIC -> item = PrideMothsMod.AROMANTIC_MOTH_JAR.get();
                case ASEXUAL -> item = PrideMothsMod.ASEXUAL_MOTH_JAR.get();
                case BISEXUAL -> item = PrideMothsMod.BISEXUAL_MOTH_JAR.get();
                case DEMIBOY -> item = PrideMothsMod.DEMIBOY_MOTH_JAR.get();
                case DEMIGENDER -> item = PrideMothsMod.DEMIGENDER_MOTH_JAR.get();
                case DEMIGIRL -> item = PrideMothsMod.DEMIGIRL_MOTH_JAR.get();
                case DEMIROMANTIC -> item = PrideMothsMod.DEMIROMANTIC_MOTH_JAR.get();
                case DEMISEXUAL -> item = PrideMothsMod.DEMISEXUAL_MOTH_JAR.get();
                case GAY -> item = PrideMothsMod.GAY_MOTH_JAR.get();
                case LESBIAN -> item = PrideMothsMod.LESBIAN_MOTH_JAR.get();
                case LGBT -> item = PrideMothsMod.LGBT_MOTH_JAR.get();
                case NON_BINARY -> item = PrideMothsMod.NON_BINARY_MOTH_JAR.get();
                case OMNISEXUAL -> item = PrideMothsMod.OMNISEXUAL_MOTH_JAR.get();
                case PANSEXUAL -> item = PrideMothsMod.PANSEXUAL_MOTH_JAR.get();
                case POLYAMOROUS -> item = PrideMothsMod.POLYAMOROUS_MOTH_JAR.get();
                case POLYSEXUAL -> item = PrideMothsMod.POLYSEXUAL_MOTH_JAR.get();
                case TRANSGENDER -> item = PrideMothsMod.TRANSGENDER_MOTH_JAR.get();
                default -> item = PrideMothsMod.MOTH_JAR.get();
            }

            ItemStack itemStack = new ItemStack(item);
            if (this.hasCustomName()) {
                itemStack.setHoverName(this.getName());
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

        return super.mobInteract(player, hand);
    }

    public void setMothVariant(MothVariation type) {
        this.entityData.set(VARIANT, type.toString());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(VARIANT, MothVariation.DEFAULT.toString());
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

    @Override
    public void tick() {
        super.tick();

        if (this.hasCustomName() && !this.isBaby()) {
            if (this.getMothVariant() != MothVariation.NON_BINARY && this.getName().getString().equalsIgnoreCase("non-binary")) {
                this.setMothVariant(MothVariation.NON_BINARY);
            } else if (this.getMothVariant() != MothVariation.TRANSGENDER && this.getName().getString().equalsIgnoreCase("trans")) {
                this.setMothVariant(MothVariation.TRANSGENDER);
            } else if (this.getMothVariant() != MothVariation.LGBT && this.getName().getString().equalsIgnoreCase("lgbt")) {
                this.setMothVariant(MothVariation.LGBT);
            } else if (this.getMothVariant() != MothVariation.GAY && this.getName().getString().equalsIgnoreCase("gay")) {
                this.setMothVariant(MothVariation.GAY);
            } else if (this.getMothVariant() != MothVariation.LESBIAN && this.getName().getString().equalsIgnoreCase("lesbian")) {
                this.setMothVariant(MothVariation.LESBIAN);
            } else if (this.getMothVariant() != MothVariation.AGENDER && this.getName().getString().equalsIgnoreCase("agender")) {
                this.setMothVariant(MothVariation.AGENDER);
            } else if (this.getMothVariant() != MothVariation.ASEXUAL && this.getName().getString().equalsIgnoreCase("asexual")) {
                this.setMothVariant(MothVariation.ASEXUAL);
            } else if (this.getMothVariant() != MothVariation.BISEXUAL && this.getName().getString().equalsIgnoreCase("bisexual")) {
                this.setMothVariant(MothVariation.BISEXUAL);
            } else if (this.getMothVariant() != MothVariation.PANSEXUAL && this.getName().getString().equalsIgnoreCase("pansexual")) {
                this.setMothVariant(MothVariation.PANSEXUAL);
            } else if (this.getMothVariant() != MothVariation.POLYAMOROUS && this.getName().getString().equalsIgnoreCase("polyamorous")) {
                this.setMothVariant(MothVariation.POLYAMOROUS);
            } else if (this.getMothVariant() != MothVariation.POLYSEXUAL && this.getName().getString().equalsIgnoreCase("polysexual")) {
                this.setMothVariant(MothVariation.POLYSEXUAL);
            } else if (this.getMothVariant() != MothVariation.OMNISEXUAL && this.getName().getString().equalsIgnoreCase("omnisexual")) {
                this.setMothVariant(MothVariation.OMNISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMISEXUAL && this.getName().getString().equalsIgnoreCase("demisexual")) {
                this.setMothVariant(MothVariation.DEMISEXUAL);
            } else if (this.getMothVariant() != MothVariation.DEMIROMANTIC && this.getName().getString().equalsIgnoreCase("demiromantic")) {
                this.setMothVariant(MothVariation.DEMIROMANTIC);
            } else if (this.getMothVariant() != MothVariation.DEMIBOY && this.getName().getString().equalsIgnoreCase("demiboy")) {
                this.setMothVariant(MothVariation.DEMIBOY);
            } else if (this.getMothVariant() != MothVariation.DEMIGIRL && this.getName().getString().equalsIgnoreCase("demigirl")) {
                this.setMothVariant(MothVariation.DEMIGIRL);
            } else if (this.getMothVariant() != MothVariation.DEMIGENDER && this.getName().getString().equalsIgnoreCase("demigender")) {
                this.setMothVariant(MothVariation.DEMIGENDER);
            } else if (this.getMothVariant() != MothVariation.AROACE && this.getName().getString().equalsIgnoreCase("aroace")) {
                this.setMothVariant(MothVariation.AROACE);
            }

            if (this.getMothVariant() != MothVariation.ALLY && this.getName().getString().equalsIgnoreCase("ally")) {
                this.setMothVariant(MothVariation.ALLY);
            } else if (this.getMothVariant() != MothVariation.ALLY && this.getName().getString().equalsIgnoreCase("straight")) {
                this.setMothVariant(MothVariation.ALLY);
            }

            if (this.getName().getString().equalsIgnoreCase("super straight")) {
                this.kill();
            } else if (this.getName().getString().equalsIgnoreCase("super_straight")) {
                this.kill();
            } else if (this.getName().getString().equalsIgnoreCase("superstraight")) {
                this.kill();
            } else if (this.getName().getString().equalsIgnoreCase("super-straight")) {
                this.kill();
            }
        }

    }

    public static class MothFlyGoal extends WaterAvoidingRandomFlyingGoal {
        public MothFlyGoal(PathfinderMob pathAwareEntity, double d) {
            super(pathAwareEntity, d);
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            Vec3 vec3d = this.mob.getViewVector(0.0F);
            Vec3 vec3d2 = HoverRandomPos.getPos(this.mob, 8, 7,
                    vec3d.x, vec3d.z, 1.5707964F, 3, 1);
            return vec3d2 != null ? vec3d2 : AirAndWaterRandomPos.getPos(this.mob, 8,
                    4, -2, vec3d.x, vec3d.z, 1.5707963705062866);
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
        // if (event.isMoving()) {
        //            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.flight", Animation.LoopType.LOOP));
        //        } else {
        //            event.getController().setAnimation(RawAnimation.begin().then("animation.moth.idle", Animation.LoopType.LOOP));
        //        }

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

    @Override
    public boolean canAttackType(EntityType<?> type) {
        return type == EntityType.PLAYER;
    }

}
