package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.EntityType;
import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class SummonRandomSeabornProcedure {
	public static boolean highEvo(LevelAccessor world){
		return Math.min(
				CaerulaArborModVariables.MapVariables.get(world).strategy_grow, 
				CaerulaArborModVariables.MapVariables.get(world).strategy_migration
			) >= 1;
	}
	public static Entity execute(LevelAccessor world, double elite_chan, double xx, double yy, double zz) {
		if (Mth.nextDouble(world.getRandom(), 0, 1) < elite_chan) {
			return SummonEliteFishProcedure.execute(world, xx, yy, zz);
		} else if (world instanceof ServerLevel _level) {
			EntityType<?> type = null;
			int rand = 0;
			if ((world.getFluidState(BlockPos.containing(xx, yy, zz)).createLegacyBlock()).getBlock() == Blocks.WATER) {
				rand = Mth.nextInt(world.getRandom(), 0, 5);
				if (world.getRandom().nextFloat() < 0.4f && highEvo(world)) rand = Mth.nextInt(world.getRandom(), 0, 7);
				if (rand == 0) {
					type = CaerulaArborModEntities.COLLECTOR_PROKARYOTE.get();
				} else if (rand == 1) {
					type = CaerulaArborModEntities.FLOATER_PROKARYOTE.get();
				} else if (rand == 2) {
					type = CaerulaArborModEntities.DEPOSITER_PROKARYOTE.get();
				} else if (rand == 3) {
					type = CaerulaArborModEntities.ACCUMULATOR_PROKARYOTE.get();
				} else if (rand == 4) {
					type = CaerulaArborModEntities.FEEDER_PROKARYOTE.get();
				} else if (rand == 5) {
					type = CaerulaArborModEntities.BONE_FISH.get();
				} else if (rand == 6) {
					type = world.getRandom().nextFloat() < 0.75f ? CaerulaArborModEntities.PROMOTED_SQUID.get(): CaerulaArborModEntities.PROMOTED_GLOW_SQUID.get();
				} else if (rand == 7) {
					type = CaerulaArborModEntities.PROMOTED_AXOLOTL.get();
				}
			} else {
				rand = Mth.nextInt(world.getRandom(), 0, 8);
				if (rand == 0) {
					type = CaerulaArborModEntities.CHISELER_FISH.get();
				} else if (rand == 1) {
					type = CaerulaArborModEntities.FLY_FISH.get();
				} else if (rand == 2) {
					type = CaerulaArborModEntities.PREDATOR_ABYSSAL.get();
				} else if (rand == 3) {
					type = CaerulaArborModEntities.FAKE_OFFSPRING.get();
				} else if (rand == 4) {
					type = CaerulaArborModEntities.SLIDER_FISH.get();
				} else if (rand == 5) {
					type = CaerulaArborModEntities.RUN_FISH.get();
				} else if (rand == 6) {
					type = CaerulaArborModEntities.SHOOTER_FISH.get();
				} else if (rand == 7) {
					type = CaerulaArborModEntities.SPLASHER_ABYSSAL.get();
				} else {
					return SummonRandomOceanizedProcedure.execute(world, xx, yy, zz);
				}
			}
			if (type != null) {
				Entity entityToSpawn = type.spawn(_level, BlockPos.containing(xx, yy, zz), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					return entityToSpawn;
				}
			}
		}
		return null;
	}
}
