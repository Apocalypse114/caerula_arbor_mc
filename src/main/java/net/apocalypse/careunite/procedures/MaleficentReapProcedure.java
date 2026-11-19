package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class MaleficentReapProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double angle = 0;
		Entity eeee = null;
		if (entity.isAlive() && entity.tickCount % 20 == 0) {
			eeee = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(eeee == null) && eeee.isAlive()) {
				for (int index0 = 0; index0 < 120; index0++) {
					angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, (x + 5 * Math.sin(angle)), (y + 0.33), (z + 5 * Math.cos(angle)), 3, 0.1, 0.1, 0.1, 0.2);
				}
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity) && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 5) {
							DeductPlayerSanityProcedure.execute(entityiterator,
									(entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity8.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 6);
						}
					}
				}
				if (entity.isInWater()) {
					MaleficentDestroyProcedure.execute(world, x, y, z, entity);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAST_SWIM.get(), 20, 2, false, false));
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MOVE_FASTER.get(), 20, 7, false, false));
				}
			}
		}
	}
}
