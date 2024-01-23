package net.dakotapride.pridemoths.client.entity;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class TravelToLightSourceGoal extends MoveToBlockGoal {
    private final MothEntity moth;

    TravelToLightSourceGoal(MothEntity moth, int range) {
        super(moth, 1.0F, range, range);
        this.moth = moth;
    }

    @Override
    public BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    public boolean canContinueToUse() {
        return this.isValidTarget(this.moth.getLevel(), this.blockPos);
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 20 == 0;
    }

    @Override
    protected boolean isValidTarget(LevelReader world, BlockPos pos) {
        return world.getBlockState(pos).is(PrideMothsMod.LIGHT_SOURCES_TAG);
    }
}
