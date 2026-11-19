package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.FlamarineGolemEntity;

public class InitFlamarineGolemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
			_livingEntity0.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).setBaseValue(5);
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(15);
		if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()))
			_livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()).setBaseValue(60);
		if (entity instanceof FlamarineGolemEntity) {
			((FlamarineGolemEntity) entity).setAnimation("animation.flamarine_golem.start");
		}
		if (entity instanceof FlamarineGolemEntity _datEntSetI)
			_datEntSetI.getEntityData().set(FlamarineGolemEntity.DATA_duration, 60);
	}
}
