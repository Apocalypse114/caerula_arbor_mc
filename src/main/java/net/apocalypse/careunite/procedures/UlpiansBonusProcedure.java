package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.UlpiansEntity;

import java.util.List;
import java.util.Comparator;

public class UlpiansBonusProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double bns = 0;
		double perc = 0;
		bns = sourceentity instanceof UlpiansEntity _datEntI ? _datEntI.getEntityData().get(UlpiansEntity.DATA_bonus) : 0;
		if (bns < 10) {
			if (sourceentity instanceof UlpiansEntity _datEntSetI)
				_datEntSetI.getEntityData().set(UlpiansEntity.DATA_bonus, (int) (bns + 1));
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (!(entityiterator instanceof LivingEntity)) {
						continue;
					}
					if (entityiterator.isAlive()) {
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:hunters")))) {
							perc = (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
							if (entityiterator instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
								_livingEntity8.getAttribute(Attributes.MAX_HEALTH).setBaseValue(
										((entityiterator instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity7.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) + 10));
							if (entityiterator instanceof LivingEntity _entity)
								_entity.setHealth((float) ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * perc));
							if (entityiterator instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
								_livingEntity12.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
										((entityiterator instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity11.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0)
												+ 2));
						}
					}
				}
			}
		}
	}
}
