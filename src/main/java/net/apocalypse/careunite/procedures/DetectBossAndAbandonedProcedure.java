package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

import java.util.List;
import java.util.Comparator;

public class DetectBossAndAbandonedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (Math.random() > 0.33) {
			return;
		}
		{
			final Vec3 _center = new Vec3((x + 0.5), (y + 1), (z + 0.5));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
					continue;
				}
				if (new Vec3((x + 0.5), (y + 1), (z + 0.5)).distanceTo(new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()))) < 24) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = CaerulaArborModEntities.THE_ABANDONED.get().spawn(_level, BlockPos.containing(x + 0.5, y, z + 0.5), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					break;
				}
			}
		}
	}
}
