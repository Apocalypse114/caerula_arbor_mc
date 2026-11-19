package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.EntityType;

public class SummonRandomOceanizedProcedure {
	public static Entity execute(LevelAccessor world, double xx, double yy, double zz) {
		int rand = Mth.nextInt(world.getRandom(), 0, 13);
		EntityType<?> type = null;
		if (world instanceof ServerLevel _level) {
			if (rand == 0) {
				type = CaerulaArborModEntities.OCEANIZED_PIG.get();
			} else if (rand == 1) {
				type = CaerulaArborModEntities.OCEANIZED_COW.get();
			} else if (rand == 2) {
				type = CaerulaArborModEntities.OCEANIZED_SHEEP.get();
			} else if (rand == 3) {
				type = CaerulaArborModEntities.OCEANIZED_HORSE.get();
			} else if (rand == 4) {
				type = CaerulaArborModEntities.OCEANIZED_WOLF.get();
			} else if (rand == 5) {
				type = CaerulaArborModEntities.OCEANIZED_SPIDER.get();
			} else if (rand == 6) {
				type = CaerulaArborModEntities.OCEANIZED_VILLAGER.get();
			} else if (rand == 7) {
				type = CaerulaArborModEntities.OCEANIZED_WITCH.get();
			} else if (rand == 8) {
				type = CaerulaArborModEntities.OCEANIZED_FOX.get();
			} else if (rand == 9) {
				type = CaerulaArborModEntities.OCEANIZED_POLAR_BEAR.get();
			} else if (rand == 10) {
				type = CaerulaArborModEntities.OCEANIZE_RABBIT.get();
			} else if (rand == 11) {
				type = CaerulaArborModEntities.OCEANIZED_CAT.get();
			} else if (rand == 12) {
				type = CaerulaArborModEntities.OCEANIZED_CHICKEN.get();
			} else if (rand == 13) {
				type = CaerulaArborModEntities.OCEANIZED_SILVERFISH.get();
			}
			if (type != null){
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
