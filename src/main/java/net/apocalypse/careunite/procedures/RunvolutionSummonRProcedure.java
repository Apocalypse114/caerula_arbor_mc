package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.EntityType;

public class RunvolutionSummonRProcedure {
	public static void execute(LevelAccessor world, Entity entity, double xx, double yy, double zz) {
		if (entity == null)
			return;
		double rand = 0;
		rand = world.getRandom().nextDouble();
		if (GetHealthPercProcedure.execute(entity) <= 0.5) {
			rand *= 1.5;
		}
		EntityType<?> type = null;
		if (world instanceof ServerLevel _level) {
			if (rand < 0.5) {
				type = CaerulaArborModEntities.TIDUTANT_EXCRESCENCE.get();
			} else if (rand < 0.75) {
				type = CaerulaArborModEntities.RUN_FISH.get();
			} else if (rand < 1) {
				type = CaerulaArborModEntities.SLIDER_FISH.get();
			} else if (rand < 1.25) {
				type = CaerulaArborModEntities.TIDUTANT_ROCK_SPIDER.get();
			} else if (rand < 1.35) {
				type = CaerulaArborModEntities.OCEANIZED_VILLAGER.get();
			} else if (rand <= 1.5) {
				type = CaerulaArborModEntities.OCEANIZED_FOX.get();
			}
			if (type != null){
				Entity entityToSapwn = type.spawn(_level, BlockPos.containing(xx, yy, zz), MobSpawnType.MOB_SUMMONED);
				if (entityToSapwn != null) {
					entityToSapwn.setYRot(world.getRandom().nextFloat() * 360F);
					EmrelySyncTeamProcedure.execute(entity, entityToSapwn);
					entityToSapwn.push((Mth.nextDouble(world.getRandom(), -0.25, 0.25)), 0, (Mth.nextDouble(world.getRandom(), -0.25, 0.25)));
				}
			}
		}
	}
}
