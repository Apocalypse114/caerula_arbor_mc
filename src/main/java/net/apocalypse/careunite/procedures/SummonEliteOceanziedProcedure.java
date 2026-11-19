package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class SummonEliteOceanziedProcedure {
	public static boolean highEvo(LevelAccessor world){
		return Math.min(
				CaerulaArborModVariables.MapVariables.get(world).strategy_grow, 
				CaerulaArborModVariables.MapVariables.get(world).strategy_migration
			) >= 1;
	}
	public static Entity execute(LevelAccessor world, double xx, double yy, double zz) {
		double rand = 0;
		rand = Mth.nextInt(world.getRandom(), 0, 5);
		if (highEvo(world)) rand = Mth.nextInt(world.getRandom(), 0, 6);

		EntityType<?> TYPE = null;
		if (world instanceof ServerLevel _level){
			if (rand == 0) {
				TYPE = CaerulaArborModEntities.OCEANIZED_VINDICATOR.get();
			} else if (rand == 1) {
				TYPE = CaerulaArborModEntities.OCEANIZED_EVOKER.get();
			} else if (rand == 2) {
				TYPE = CaerulaArborModEntities.OCEANIZED_RAVAGER.get();
			} else if (rand == 3) {
				TYPE = CaerulaArborModEntities.OCEANIZED_ENDERMAN.get();
			} else if (rand == 4) {
				TYPE = CaerulaArborModEntities.COMPASSION_PRAYER.get();
			}
 else if (rand == 5) {
				TYPE = CaerulaArborModEntities.OCEANIZED_LLAMA.get();
			} else if (rand == 6) {
				TYPE = CaerulaArborModEntities.PROMOTED_GUARDIAN.get();
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
