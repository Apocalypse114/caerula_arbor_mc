package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class FormArmorBossProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.FLAMARINE_GOLEM.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
		}
		{
			BlockPos _pos = BlockPos.containing(x, y, z);
			Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
			world.destroyBlock(_pos, false);
		}
	}
}
