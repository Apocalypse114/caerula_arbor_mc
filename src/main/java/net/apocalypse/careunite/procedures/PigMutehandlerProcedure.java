package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedPigEntity;

public class PigMutehandlerProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.isAlive()) {
			if (entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.MUTE.get())) {
				if (entity instanceof OceanizedPigEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedPigEntity.DATA_mute_time,
							entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.MUTE.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.MUTE.get()).getDuration() : 0);
			} else if ((entity instanceof OceanizedPigEntity _datEntI ? _datEntI.getEntityData().get(OceanizedPigEntity.DATA_mute_time) : 0) == 1) {
				if (entity instanceof OceanizedPigEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedPigEntity.DATA_mute_time, 0);
			}
		}
	}
}
