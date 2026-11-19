package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.AbsorberLimbEntity;

public class InitAbserLimbProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity)
			_entity.setHealth((float) (((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 2) / 3));
		if (entity instanceof AbsorberLimbEntity) {
			((AbsorberLimbEntity) entity).setAnimation("animation.absorber_limb.start");
		}
	}
}
