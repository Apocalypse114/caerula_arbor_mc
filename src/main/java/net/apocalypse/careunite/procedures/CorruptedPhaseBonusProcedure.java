package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

import java.util.List;
import java.util.Comparator;

public class CorruptedPhaseBonusProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double phase) {
		if (entity == null)
			return;
		double ddd = 0;
		double healPerc = 0;
		boolean mayBonus = false;
		boolean isSeaborn = false;
		Entity enemy = null;
		ddd = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		healPerc = 0.1;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (phase > 0.5) {
			healPerc = 0.2;
		}
		if (phase <= 1) {
			LivingHeal1Procedure.execute(entity, ddd * healPerc * 3);
			if (phase > 0.5) {
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_deal, (int) ((entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_deal) : 0) - ddd * healPerc * 3));
			}
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				mayBonus = false;
				isSeaborn = false;
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 16) {
					if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
						mayBonus = true;
						isSeaborn = true;
					}
					if (phase <= 1 && entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:is_humanside")))) {
						mayBonus = true;
						isSeaborn = false;
					}
					if (entityiterator == entity) {
						continue;
					}
					if (mayBonus) {
						LivingHeal1Procedure.execute(entityiterator, ddd * healPerc);
						if (phase == 1 && !entityiterator.getPersistentData().getBoolean("corruptedBonus1")) {
							if (entityiterator instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
								_livingEntity10.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
										((entityiterator instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0)
												+ ddd * 0.4));
							if (entityiterator instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
								_livingEntity12.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
										.setBaseValue(((entityiterator instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
												? _livingEntity11.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
												: 0) + ddd * 0.4));
							entityiterator.getPersistentData().putBoolean("corruptedBonus1", true);
						}
						if (phase == 2 && !entityiterator.getPersistentData().getBoolean("corruptedBonus2")) {
							if (entityiterator instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
								_livingEntity16.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
										((entityiterator instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0)
												+ ddd));
							if (entityiterator instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
								_livingEntity18.getAttribute(Attributes.MAX_HEALTH).setBaseValue(
										((entityiterator instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity17.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) + ddd));
							entityiterator.getPersistentData().putBoolean("corruptedBonus2", true);
						}
						if (phase > 0.5 && isSeaborn) {
							if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))
									&& !entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanpet")))) {
								if (entityiterator instanceof Mob _entity && enemy instanceof LivingEntity _ent)
									_entity.setTarget(_ent);
							}
						}
					}
				}
			}
		}
	}
}
