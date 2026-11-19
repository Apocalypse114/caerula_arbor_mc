package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.CompassionPrayerEntity;

import java.util.List;
import java.util.Comparator;

public class PrayerTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double dura = 0;
		double P = 0;
		double perc = 0;
		double d = 0;
		if (!entity.isAlive()) {
			return;
		}
		dura = entity instanceof CompassionPrayerEntity _datEntI ? _datEntI.getEntityData().get(CompassionPrayerEntity.DATA_REVIVE_TICK) : 0;
		if (dura > 0) {
			if (dura <= 100) {
				if (entity instanceof CompassionPrayerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(CompassionPrayerEntity.DATA_PHASE, 1);
			}
			if (dura == 198) {
				PrayerRebornProcedure.execute(world, x, y, z, entity);
			}
			if (entity instanceof CompassionPrayerEntity _datEntSetI)
				_datEntSetI.getEntityData().set(CompassionPrayerEntity.DATA_REVIVE_TICK, (int) (dura - 1));
			entity.setShiftKeyDown(true);
		} else {
			entity.setShiftKeyDown(false);
		}
		P = entity instanceof CompassionPrayerEntity _datEntI ? _datEntI.getEntityData().get(CompassionPrayerEntity.DATA_PHASE) : 0;
		if (P == 0) {
			if (entity instanceof CompassionPrayerEntity animatable)
				animatable.setTexture("compassion_prayer");
			if (entity.tickCount % 10 == 0) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.isAlive()) {
							continue;
						}
						if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity)) {
							continue;
						}
						if (IsSeabornProcedure.execute(entityiterator)) {
							if (IsSeabornBossProcedure.execute(entityiterator)) {
								continue;
							}
							if (!(entityiterator instanceof LivingEntity _livEnt10 && _livEnt10.hasEffect(CaerulaArborModMobEffects.ADD_HEALTH_PERCLY.get()))) {
								perc = GetHealthPercProcedure.execute(entityiterator);
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_HEALTH_PERCLY.get(), 32768, 1, false, false));
								if (entityiterator instanceof LivingEntity _entity)
									_entity.setHealth((float) ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * perc));
							}
						}
					}
				}
			}
		} else {
			if (entity instanceof CompassionPrayerEntity animatable)
				animatable.setTexture("compassion_prayer_a");
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			d = entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity17.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
			if (entity.tickCount % 20 == 0) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!(entityiterator instanceof LivingEntity)) {
							continue;
						}
						if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity) && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 5) {
							entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic")))),
									(float) (d * 0.2));
							DeductPlayerSanityProcedure.execute(entityiterator, d * 30);
						}
					}
				}
			}
			PrayerRimProcedure.execute(world, x, y, z);
		}
	}
}
