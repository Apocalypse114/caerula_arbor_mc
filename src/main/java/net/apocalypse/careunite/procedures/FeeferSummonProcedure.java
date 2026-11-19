package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.FeederProkaryoteEntity;
import net.minecraft.world.entity.EntityType;

import net.apocalypse.careunite.procedures.MarineDropTombProcedure;

public class FeeferSummonProcedure {

	private static boolean shouldClone(Entity entity){
		return entity instanceof FeederProkaryoteEntity _datEntL0 
			&& _datEntL0.getEntityData().get(FeederProkaryoteEntity.DATA_SHOULD_CLONE);
	}
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level){
			MarineDropTombProcedure.execute(world, x, y, z, entity);
			_level.sendParticles(ParticleTypes.CLOUD, x, y, z, 32, 0.5, 0.5, 0.5, 0.1);
			if (CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation >= 1 
				&& shouldClone(entity)) {
				Entity entityToSpawn = CaerulaArborModEntities.FEEDER_PROKARYOTE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn instanceof FeederProkaryoteEntity _datEntSetL) {
					EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					_datEntSetL.getEntityData().set(FeederProkaryoteEntity.DATA_SHOULD_CLONE, false);
				}
				return;
			}
			double rand = world.getRandom().nextDouble();
			EntityType<?> type = null;
			if (rand < 0.2) {
				 type = CaerulaArborModEntities.DEPOSITER_PROKARYOTE.get();
			} else if (rand < 0.7) {
				type = CaerulaArborModEntities.ACCUMULATOR_PROKARYOTE.get();
			} else {
				type = CaerulaArborModEntities.COLLECTOR_PROKARYOTE.get();
			}
			if (type != null){
				Entity toSpawn = type.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (toSpawn != null) {
					EmrelySyncTeamProcedure.execute(entity, toSpawn);
					toSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (CaerulaArborModVariables.MapVariables.get(world).strategy_breed >= 4) {
				if (world.getRandom().nextDouble() <= 0.33) {
					rand = world.getRandom().nextDouble();
					if (rand < 0.35) {
						type = CaerulaArborModEntities.APOSTLE_PROKARYOTE.get();
					} else if (rand < 0.65) {
						type = CaerulaArborModEntities.CREEPER_FISH.get();
					} else if (rand < 0.85) {
						type = CaerulaArborModEntities.PUNCTURE_FISH.get();
					} else if (rand < 0.95) {
						type = CaerulaArborModEntities.NUCLEIC_MALEFICENT.get();
					} else {
						type = CaerulaArborModEntities.CRACKER_ABYSSAL.get();
					}
					if (type != null){
						Entity toSpawn = type.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
						if (toSpawn != null) {
							EmrelySyncTeamProcedure.execute(entity, toSpawn);
							toSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				}
			}
			if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 4) {
				if (world.getRandom().nextDouble() <= 0.001 && GetSeabornAroundProcedure.execute(world, x, y, z, entity) >= 8) {
					rand = world.getRandom().nextDouble();
					if (rand < 0.45) {
						type = CaerulaArborModEntities.ROUTE_SHAPER.get();
					} else if (rand < 0.9) {
						type = CaerulaArborModEntities.OCEANIZED_RAVAGER.get();
					} else if (rand < 0.97) {
						type = CaerulaArborModEntities.OCEANIZED_BRUTE.get();
					} else if (rand < 0.99) {
						type = CaerulaArborModEntities.OCEANIZED_ENDERMAN.get();
					} else {
						type = CaerulaArborModEntities.SUPER_BIG_CAT.get();
					}
					if (type != null){
						Entity toSpawn = type.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
						if (toSpawn != null) {
							EmrelySyncTeamProcedure.execute(entity, toSpawn);
							toSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				}
			}
		}
	}
}
