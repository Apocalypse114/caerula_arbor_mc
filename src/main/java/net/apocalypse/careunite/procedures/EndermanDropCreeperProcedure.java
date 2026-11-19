package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.OceanizedEndermanEntity;
import net.apocalypse.careunite.entity.CreeperFishEntity;

public class EndermanDropCreeperProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedEndermanEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedEndermanEntity.DATA_HOLDING_CrEEPER)) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.CREEPER_FISH.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn instanceof CreeperFishEntity creeper) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					if (_datEntL0.getCreeperCharged()) creeper.setCharged();
					EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
				}
			}
		}
	}
}
