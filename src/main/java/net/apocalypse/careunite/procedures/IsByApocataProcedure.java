package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class IsByApocataProcedure {
	public static boolean execute(Entity sourceentity) {
		if (sourceentity == null)
			return false;
		return (sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.APOCATA_SWORD.get()) : false;
	}
}
