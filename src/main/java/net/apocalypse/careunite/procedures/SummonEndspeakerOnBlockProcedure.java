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

public class SummonEndspeakerOnBlockProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, ItemStack itemstack) {
		if (direction == null)
			return InteractionResult.PASS;
		double tgtX = 0;
		double tgtY = 0;
		double tgtZ = 0;
		double phase = 0;
		tgtX = x + direction.getStepX() + 0.5;
		tgtY = y + direction.getStepY() + 0.5;
		tgtZ = z + direction.getStepZ() + 0.5;
		phase = itemstack.getOrCreateTag().getDouble("phase");
		if (phase == 3) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.ENDSPEAKER_3.get().spawn(_level, BlockPos.containing(tgtX, tgtY, tgtZ), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		} else if (phase == 2) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.ENDSPEAKER_2.get().spawn(_level, BlockPos.containing(tgtX, tgtY, tgtZ), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		} else if (phase == 1) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.ENDSPEAKER_1.get().spawn(_level, BlockPos.containing(tgtX, tgtY, tgtZ), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		} else {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.ENDSPEAKER_0.get().spawn(_level, BlockPos.containing(tgtX, tgtY, tgtZ), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		}
		itemstack.shrink(1);
		return InteractionResult.SUCCESS;
	}
}
