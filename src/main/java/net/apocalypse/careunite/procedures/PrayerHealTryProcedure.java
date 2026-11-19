package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.entity.CompassionPrayerEntity;

import java.util.List;
import java.util.Comparator;

public class PrayerHealTryProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double count = 0;
		double atk = 0;
		if ((entity instanceof CompassionPrayerEntity _datEntI ? _datEntI.getEntityData().get(CompassionPrayerEntity.DATA_PHASE) : 0) > 0) {
			return;
		}
		atk = entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entity == entityiterator) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
						if (entityiterator.isAlive()) {
							LivingHeal1Procedure.execute(entityiterator, atk);
							if (world instanceof ServerLevel _level)
								_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.SEA_SPLASH.get()), (entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ()), 24, 1, 1, 1, 0.1);
							count = count + 1;
							if (count >= 3) {
								break;
							}
						}
					}
				}
			}
		}
	}
}
