package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class TombCallSeabornsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			if (entity.tickCount % 20 == 0) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:tombstone")))) {
							continue;
						}
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
							continue;
						}
						if (entityiterator.getPersistentData().getBoolean("caerulaTombEaten")) {
							continue;
						}
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
							OnTombstoneEatenProcedure.execute(world, x, y, z, entityiterator, entity);
							break;
						}
					}
				}
			}
			if (entity.tickCount % 100 == 0) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(72 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:tombstone")))) {
							continue;
						}
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
							continue;
						}
						if (entityiterator.getPersistentData().getBoolean("caerulaTombEaten")) {
							continue;
						}
						if (entityiterator instanceof LivingEntity _livEnt11 && _livEnt11.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get())) {
							continue;
						}
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring"))) && !(entityiterator instanceof Mob _mobEnt13 && _mobEnt13.isAggressive())) {
							if (entityiterator instanceof Mob _entity)
								_entity.getNavigation().moveTo(x, y, z, 1.25);
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 100, 0, false, false));
						}
					}
				}
			}
		}
	}
}
