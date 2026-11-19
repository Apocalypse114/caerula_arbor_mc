package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizeRabbitEntity;

public class SwitchToXLPJProcedure {
	public static InteractionResult execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if ((entity instanceof OceanizeRabbitEntity _datEntI ? _datEntI.getEntityData().get(OceanizeRabbitEntity.DATA_variant) : 0) > 4.5) {
			return InteractionResult.PASS;
		}
		if ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.APOCALYPSE.get()) : false) {
			if (entity instanceof OceanizeRabbitEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizeRabbitEntity.DATA_variant, 5);
			if (entity instanceof OceanizeRabbitEntity animatable)
				animatable.setTexture("oceanized_rabbit_bloody");
			if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity5.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 10));
			if (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				_livingEntity8.getAttribute(Attributes.MAX_HEALTH)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity7.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 10));
			if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
				_livingEntity9.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).setBaseValue(32.5);
			if (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
				_livingEntity10.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(79.9);
			if (entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.KNOCKBACK_RESISTANCE))
				_livingEntity11.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 2));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, 2));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 99999, 0));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SANITY_IMMUE.get(), 99999, 0));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 1));
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
