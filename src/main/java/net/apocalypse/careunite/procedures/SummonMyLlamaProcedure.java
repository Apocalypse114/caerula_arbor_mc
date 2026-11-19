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
import net.minecraft.core.particles.ParticleTypes;

public class SummonMyLlamaProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedWanderingTraderEntity _datEntSetL)
{
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_LLAMA.get().spawn(_level, BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -2, 2), y, z + Mth.nextInt(RandomSource.create(), -2, 2)), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn instanceof Mob mob) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
					_level.sendParticles(ParticleTypes.CLOUD, mob.getX(), mob.getY(), mob.getZ(), 8, 0.75, 0.75, 0.75, 0.1);
					mob.setLeashedTo(entity, true);
					_datEntSetL.putLlama(mob);
				}
			}
		}
	}
}
