package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class HoglinResetHealthProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity passenger = null;
		double myH = 0;
		double myMaxH = 0;
		double hisH = 0;
		double hisMaxH = 0;
		double perc = 0;
		if (entity.isVehicle() && entity.isAlive()) {
			passenger = entity.getControllingPassenger();
			if (!(passenger == null) && passenger.isAlive()) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 2, false, false));
				myH = entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1;
				hisH = passenger instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1;
				myMaxH = entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
				hisMaxH = passenger instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
				if (hisMaxH <= myMaxH && hisH > 0) {
					perc = (myH + hisH) / (myMaxH + hisMaxH);
					if (hisMaxH * perc > hisH) {
						if (entity instanceof LivingEntity _entity)
							_entity.setHealth((float) (myMaxH * perc));
						if (passenger instanceof LivingEntity _entity)
							_entity.setHealth((float) (hisMaxH * perc));
					}
				}
			}
		}
	}
}
