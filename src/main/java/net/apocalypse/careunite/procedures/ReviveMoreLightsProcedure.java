package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class ReviveMoreLightsProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		RevivesPlayerLightsProcedure.execute(entity, 24);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 3200, 2));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get(), 3600, 1));
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.BLINDNESS);
	}
}
