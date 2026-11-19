package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class NeuronalRevivalProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		RevivesPlayerSanityProcedure.execute(entity, 1000);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.BLINDNESS);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.DARKNESS);
	}
}
