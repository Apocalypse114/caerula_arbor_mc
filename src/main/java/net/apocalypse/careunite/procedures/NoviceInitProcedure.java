package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.NoviceAlchemistEntity;

public class NoviceInitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (Math.random() < 0.07) {
			if (entity instanceof NoviceAlchemistEntity _datEntSetL)
				_datEntSetL.getEntityData().set(NoviceAlchemistEntity.DATA_IS_BLUE, true);
		}
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(40);
	}
}
