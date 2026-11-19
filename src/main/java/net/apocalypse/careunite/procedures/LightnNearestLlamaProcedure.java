package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;
import net.minecraft.world.entity.LivingEntity;

public class LightnNearestLlamaProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity instanceof OceanizedWanderingTraderEntity trd)
{
			LivingEntity llama = trd.lightLlama();
			if (llama == null) return;
			if (world instanceof ServerLevel _level) {
				LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
				entityToSpawn.moveTo(llama.position());
				entityToSpawn.setVisualOnly(true);
				_level.addFreshEntity(entityToSpawn);
				llama.thunderHit(_level, entityToSpawn);
			}
		}
	}
}
