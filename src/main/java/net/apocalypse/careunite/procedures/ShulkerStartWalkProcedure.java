package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedShulkerEntity;

public class ShulkerStartWalkProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!(entity instanceof OceanizedShulkerEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedShulkerEntity.DATA_WALKING))) {
			if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				_livingEntity2.getAttribute(Attributes.MAX_HEALTH)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity1.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 0.6));
			if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ARMOR))
				_livingEntity4.getAttribute(Attributes.ARMOR)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity3.getAttribute(Attributes.ARMOR).getBaseValue() : 0) * 0.4));
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 20, 9, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 9, false, false));
			if (entity instanceof OceanizedShulkerEntity) {
				((OceanizedShulkerEntity) entity).setAnimation("animation.oceanized_shulker.rise");
			}
			if (entity instanceof OceanizedShulkerEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceanizedShulkerEntity.DATA_WALKING, true);
			if (entity instanceof OceanizedShulkerEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedShulkerEntity.DATA_PEEK_TIME, 0);
			return true;
		}
		return false;
	}
}
