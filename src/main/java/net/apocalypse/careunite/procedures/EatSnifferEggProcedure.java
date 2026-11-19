package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class EatSnifferEggProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		AddLivingBarrierProcedure.execute(entity, 10);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COUNTER_BACK.get(), 400, 1));
	}
}
