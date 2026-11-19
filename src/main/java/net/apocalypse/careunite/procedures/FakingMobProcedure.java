package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TideDeathrepellerEntity;

public class FakingMobProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if ((entity instanceof TideDeathrepellerEntity _datEntI ? _datEntI.getEntityData().get(TideDeathrepellerEntity.DATA_duration) : 0) > 0) {
			return false;
		}
		return !(entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get()));
	}
}
