package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class PutDownBallProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, ItemStack itemstack) {
		if (direction == null)
			return InteractionResult.PASS;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.SLIDER_BALL.get().spawn(_level, BlockPos.containing(x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ()), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
		}
		itemstack.shrink(1);
		return InteractionResult.SUCCESS;
	}
}
