package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;
import net.minecraft.world.entity.Mob;

public class WanderingSummonLlamaProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
{
			_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_TRADE_LEFT, Mth.nextInt(RandomSource.create(), 8, 16));
			if (world instanceof ServerLevel _level) {
				 for (int index0 = 0; index0 < 2; index0++) {
					Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_LLAMA.get().spawn(_level, BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -2, 2), y, z + Mth.nextInt(RandomSource.create(), -2, 2)), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn instanceof Mob mob) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
						mob.setLeashedTo(entity, true);
						_datEntSetI.putLlama(mob);
					}
				}
			}
		}
	}
}
