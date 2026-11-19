package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class GetPullingProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		if ((entity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.CHITIN_BOW.get()) : false) {
			return entity instanceof LivingEntity _entUseTicks1 ? _entUseTicks1.getTicksUsingItem() : 0;
		}
		return 0;
	}
}
