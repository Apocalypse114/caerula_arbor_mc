package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class PlayerEvoFinalHurtProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		double barrier = 0;
		double rate = 0;
		double max = 0;
		double lvl = 0;
		if (sourceentity instanceof Player && CanPlayerEvoProcedure.execute(sourceentity)) {
			barrier = sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get())
					? _livingEntity1.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()).getBaseValue()
					: 0;
			lvl = GetNodeLivingBarrierProcedure.execute(sourceentity);
			if (lvl >= 4) {
				rate = 0.3;
				max = 10;
			} else if (lvl >= 3) {
				rate = 0.18;
				max = 10;
			} else if (lvl >= 2) {
				rate = 0.09;
				max = 5;
			} else if (lvl >= 1) {
				rate = 0.03;
				max = 5;
			}
			max = max * (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
			if (barrier < max && rate > 0) {
				if (sourceentity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()))
					_livingEntity3.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()).setBaseValue(Math.min(barrier + amount * rate, max));
			}
			rate = 0;
			lvl = GetNodeRealDamageProcedure.execute(sourceentity);
			if (lvl >= 4) {
				rate = 0.3;
			} else if (lvl >= 3) {
				rate = 0.18;
			} else if (lvl >= 2) {
				rate = 0.09;
			} else if (lvl >= 1) {
				rate = 0.03;
			}
			if (rate > 0) {
				PEVODealRealDamageProcedure.execute(world, x, y, z, entity, sourceentity, amount, rate);
			}
		}
	}
}
