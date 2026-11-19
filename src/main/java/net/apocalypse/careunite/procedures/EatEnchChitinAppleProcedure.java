package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class EatEnchChitinAppleProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
				? _livingEntity0.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()).getBaseValue()
				: 0) < 3) {
			AddLayeredBarrierProcedure.execute(entity, 1);
		}
		if (Math.random() < 0.5 && (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
				? _livingEntity1.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()).getBaseValue()
				: 0) < 3) {
			AddLayeredBarrierProcedure.execute(entity, 1);
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 600, 11, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 0, false, false));
	}
}
