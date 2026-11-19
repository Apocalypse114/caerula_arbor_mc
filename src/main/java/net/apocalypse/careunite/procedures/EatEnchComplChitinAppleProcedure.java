package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class EatEnchComplChitinAppleProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		for (int index0 = 0; index0 < 2; index0++) {
			if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
					? _livingEntity0.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()).getBaseValue()
					: 0) < 5) {
				AddLayeredBarrierProcedure.execute(entity, 1);
			}
		}
		AddLivingBarrierProcedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.25);
		if (Math.random() < 0.5 && (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
				? _livingEntity2.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()).getBaseValue()
				: 0) < 5) {
			AddLayeredBarrierProcedure.execute(entity, 1);
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 700, 13, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MAGIC_RESIS_BUFF.get(), 700, 9, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REGENERATION_PERCLY.get(), 240, 1, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SANITY_IMMUE.get(), 300, 0, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IMMORTAL.get(), 360, 0, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 650, 7, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 650, 1, false, false));
	}
}
