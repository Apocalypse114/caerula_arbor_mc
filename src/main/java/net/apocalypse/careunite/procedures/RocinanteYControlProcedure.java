package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class RocinanteYControlProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity passanger = null;
		double yRot = 0;
		if (entity.tickCount % 5 == 0) {
			passanger = entity.getFirstPassenger();
			if (passanger == null) {
				return;
			}
			yRot = (-1) * passanger.getXRot();
			if (GetSpeedProcedure.execute(entity) > 0) {
				if (yRot > 15 || yRot < -15) {
					entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()),
							((entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity5.getAttribute(Attributes.MOVEMENT_SPEED).getValue() : 0)
									* Math.sin(Math.toRadians(yRot))),
							(entity.getDeltaMovement().z())));
				} else {
					entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), 0, (entity.getDeltaMovement().z())));
				}
			}
		}
	}
}
