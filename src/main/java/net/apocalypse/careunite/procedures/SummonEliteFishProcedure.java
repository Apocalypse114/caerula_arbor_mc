package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class SummonEliteFishProcedure {
	public static boolean highEvo(LevelAccessor world){
		return Math.min(
				CaerulaArborModVariables.MapVariables.get(world).strategy_grow, 
				CaerulaArborModVariables.MapVariables.get(world).strategy_migration
			) >= 1;
	}
	public static Entity execute(LevelAccessor world, double xx, double yy, double zz) {
		int rand = 0;
		EntityType<?> TYPE = null;
		if (world instanceof ServerLevel _level) {
			if (world.isWaterAt(BlockPos.containing(xx, yy, zz))) {
				rand = Mth.nextInt(world.getRandom(), 0, 1);
				if (world.getRandom().nextFloat() < 0.1f && highEvo(world)) rand = Mth.nextInt(world.getRandom(), 0, 2);
				if (rand == 0) {
					TYPE = CaerulaArborModEntities.APOSTLE_PROKARYOTE.get();
				} else if (rand == 1) {
					TYPE = CaerulaArborModEntities.NUCLEIC_MALEFICENT.get();
				}
 else if (rand == 2){
					TYPE = CaerulaArborModEntities.PROMOTED_GUARDIAN.get();
				}
			} else {
				int max = 9;
				if (world.getRandom().nextFloat() < 0.5f) max = 10;
				rand = Mth.nextInt(world.getRandom(), 0, max);
				if (rand == 0) {
					TYPE = CaerulaArborModEntities.BASELAYER_ABYSSAL.get();
				} else if (rand == 1) {
					TYPE = CaerulaArborModEntities.CRACKER_ABYSSAL.get();
				} else if (rand == 2) {
					TYPE = CaerulaArborModEntities.CREEPER_FISH.get();
				} else if (rand == 3) {
					TYPE = CaerulaArborModEntities.GUIDE_ABYSSAL.get();
				} else if (rand == 4) {
					TYPE = CaerulaArborModEntities.PUNCTURE_FISH.get();
				} else if (rand == 5) {
					TYPE = CaerulaArborModEntities.REAPER_FISH.get();
				} else if (rand == 6) {
					TYPE = CaerulaArborModEntities.UMBRELLA_ABYSSAL.get();
				} else if (rand == 7) {
					TYPE = CaerulaArborModEntities.PREGNANT_FISH.get();
				} else if (rand == 8) {
					TYPE = CaerulaArborModEntities.FLEE_FISH.get();
				} else if (rand == 9) {
					TYPE = CaerulaArborModEntities.CHEST_FISH.get();
				} else if (rand == 10) {
					return SummonEliteOceanziedProcedure.execute(world, xx, yy, zz);
				}
			}
			if (TYPE == null) return null;
			Entity entityToSpawn = TYPE.spawn(_level, BlockPos.containing(xx, yy, zz), MobSpawnType.MOB_SUMMONED);
			_level.sendParticles(ParticleTypes.CLOUD, xx, yy, zz, 32, 1, 1, 1, 0.1);
			if (entityToSpawn instanceof LivingEntity living) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				return entityToSpawn;
			}
		}
		return null;
	}
}
