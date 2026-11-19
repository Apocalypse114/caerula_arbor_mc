package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;

public class WanderingAddPhaseProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		double p = 0;
		p = entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_PHASE) : 0;
		if (p == 0) {
			if (entity instanceof OceanizedWanderingTraderEntity) {
				((OceanizedWanderingTraderEntity) entity).setAnimation("to_2");
			}
			if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_ACTION_TICK, 13);
			if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_PHASE, 1);
			if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity5.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 2));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 13, 9, false, false));
			return true;
		}
		return false;
	}
}
