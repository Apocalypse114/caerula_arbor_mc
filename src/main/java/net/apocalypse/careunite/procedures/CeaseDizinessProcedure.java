package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class CeaseDizinessProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.FROZEN.get());
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.DIG_SLOWDOWN);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.DARKNESS);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.BLINDNESS);
	}
}
