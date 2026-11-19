package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class ImmueToSnowProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.setTicksFrozen(0);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.FROZEN.get());
	}
}
