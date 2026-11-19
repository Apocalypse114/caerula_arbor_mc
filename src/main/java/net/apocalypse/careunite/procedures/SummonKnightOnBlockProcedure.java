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

public class SummonKnightOnBlockProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity, ItemStack itemstack) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		if (entity.isShiftKeyDown()) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.LAST_KNIGHT_AND_HORSE.get().spawn(_level, BlockPos.containing(x + direction.getStepX() + 0.5, y + direction.getStepY() + 0.5, z + direction.getStepZ() + 0.5), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		} else {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.THE_LAST_KNIGHT.get().spawn(_level, BlockPos.containing(x + direction.getStepX() + 0.5, y + direction.getStepY() + 0.5, z + direction.getStepZ() + 0.5), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		}
		itemstack.shrink(1);
		return InteractionResult.SUCCESS;
	}
}
