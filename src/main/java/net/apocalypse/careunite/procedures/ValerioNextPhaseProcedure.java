package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.SaintValerioEntity;

public class ValerioNextPhaseProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		double P = 0;
		if ((entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_PHASE) : 0) < 0.5
				&& (entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_REVIVE_T) : 0) < 0.5) {
			if (entity instanceof SaintValerioEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_REVIVE_T, 200);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 9, false, false));
			if (entity instanceof SaintValerioEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_IGNITE_T, 0);
			if (entity instanceof SaintValerioEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_SHOOT_T, 0);
			if (entity instanceof SaintValerioEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_SHOOT_COOLDOWN, 400);
			if (entity instanceof SaintValerioEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_PIERCE_T, 0);
			if (entity instanceof SaintValerioEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_ATTACK_ANIM, 0);
			if (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				_livingEntity10.getAttribute(Attributes.MAX_HEALTH)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity9.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) + 100));
			if (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ARMOR))
				_livingEntity12.getAttribute(Attributes.ARMOR)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity11.getAttribute(Attributes.ARMOR).getBaseValue() : 0) - 6));
			if (entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
				_livingEntity14.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
						.setBaseValue(((entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
								? _livingEntity13.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
								: 0) + 50));
			return true;
		}
		return false;
	}
}
