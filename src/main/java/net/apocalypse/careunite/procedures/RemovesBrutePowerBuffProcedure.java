package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;

public class RemovesBrutePowerBuffProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!((entity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.BRUTE_AXE.get()) : false)) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.BRUTE_POWER.get());
		}
	}
}
