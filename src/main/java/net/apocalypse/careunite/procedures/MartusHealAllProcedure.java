package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class MartusHealAllProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof Mob)) {
					continue;
				}
				if (!entityiterator.isAlive()) {
					continue;
				}
				if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity)) {
					continue;
				}
				if (IsSeabornProcedure.execute(entityiterator) && !IsSeabornBossProcedure.execute(entityiterator)) {
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 200, 1));
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 200, 0));
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.DOLPHIN, (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 32, 0.85, 0.85, 0.85, 0.1);
				}
			}
		}
	}
}
