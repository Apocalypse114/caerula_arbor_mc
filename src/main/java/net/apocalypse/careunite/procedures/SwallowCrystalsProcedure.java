package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;

import java.util.List;
import java.util.Comparator;

public class SwallowCrystalsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double count = 0;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof MoistEnderCrystalEntity && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 2.5) {
					if (!entityiterator.level().isClientSide())
						entityiterator.discard();
					LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.05);
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.DRAGON_BREATH, (entityiterator.getX()), (entityiterator.getY() + 0.5), (entityiterator.getZ()), 16, 0.5, 0.5, 0.5, 0.1);
				}
			}
		}
	}
}
