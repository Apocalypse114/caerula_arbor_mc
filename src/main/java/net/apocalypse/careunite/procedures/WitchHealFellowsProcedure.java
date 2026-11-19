package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class WitchHealFellowsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get()))) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity)) {
						continue;
					}
					if (Math.random() < 0.33 && IsSeabornProcedure.execute(entityiterator)) {
						ThrowRandomPotionProcedure.execute(world, entity, entityiterator, entityiterator.getX(), entityiterator.getY() + entityiterator.getBbHeight(), entityiterator.getZ());
						if (entityiterator instanceof LivingEntity _entity)
							_entity.removeEffect(MobEffects.POISON);
						if (entityiterator instanceof LivingEntity _entity)
							_entity.removeEffect(MobEffects.WEAKNESS);
						if (entityiterator instanceof LivingEntity _entity)
							_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
					}
				}
			}
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 100, 0, false, false));
		}
	}
}
