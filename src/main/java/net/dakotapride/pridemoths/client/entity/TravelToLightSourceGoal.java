package net.dakotapride.pridemoths.client.entity;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TravelToLightSourceGoal extends MoveToBlockGoal {
    private final MothEntity moth;

    public TravelToLightSourceGoal(MothEntity entity, int range) {
        super(entity, 1.0F, range, range);
        this.moth = entity;
    }

    protected int nextStartTick(PathfinderMob mob) {
        return reducedTickDelay(50 + moth.getRandom().nextInt(50));
    }

    @Override
    public boolean canUse() {
        return this.moth.lightPos == null && super.canUse() && !isTargetBlocked(blockPos.getCenter());
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(mob.getX(), mob.getEyeY(), mob.getZ());
        return mob.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob)).getType() != HitResult.Type.MISS;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.moth.lightPos == null;
    }

    public double acceptedDistance() {
        return moth.getBbWidth() + 1;
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos target = getMoveToTarget();
        if (target != null) {
            moth.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(target));
            if (this.isReachedTarget()) {
                moth.lightPos = blockPos;
            }
        }
    }


    public void start() {
        //moth.setFlying(true);
        super.start();
    }

    public void stop() {
        super.stop();
    }

    protected @NotNull BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
        if (pos != null &&
                worldIn.getBlockState(pos).is(PrideMothsMod.LIGHT_SOURCES_TAG) &&
                worldIn.getLightEmission(pos) > 0 && worldIn instanceof ServerLevel) {
            return true;
        } else {
            return false;
        }
    }
}
