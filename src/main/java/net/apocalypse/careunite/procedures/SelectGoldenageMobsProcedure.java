package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.EntityType;
import java.util.List;
import java.util.ArrayList;

public class SelectGoldenageMobsProcedure {

	public static List<EntityType<?>> candidate = new ArrayList<>();
	
	public static Entity execute(LevelAccessor world, double xx, double yy, double zz) {
		int rand = Mth.nextInt(world.getRandom(), 0, 5);
		if (candidate.isEmpty()){
			candidate = List.of(
				CaerulaArborModEntities.BASELAYER_ABYSSAL.get(), CaerulaArborModEntities.PREDATOR_ABYSSAL.get(),
				CaerulaArborModEntities.GUIDE_ABYSSAL.get(), CaerulaArborModEntities.SPLASHER_ABYSSAL.get(),
				CaerulaArborModEntities.UMBRELLA_ABYSSAL.get(), CaerulaArborModEntities.CRACKER_ABYSSAL.get()
			);
		}
		EntityType<?> selected = candidate.get(rand);
		if (selected != null && world instanceof ServerLevel _level) {
			Entity entityToSpawn = selected.spawn(_level, BlockPos.containing(xx, yy, zz), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				_level.sendParticles(ParticleTypes.CLOUD, xx, (yy + 0.75), zz, 64, 0.75, 0.75, 0.75, 0.1);
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				return entityToSpawn;
			}
		}
		return null;
	}
}
