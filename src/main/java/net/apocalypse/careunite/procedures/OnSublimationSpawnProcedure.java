package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class OnSublimationSpawnProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double finalGrow = 0;
		finalGrow = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation, CaerulaArborModVariables.MapVariables.get(world).strategy_grow);
		if (finalGrow > 0) {
			if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
				_livingEntity1.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
						.setBaseValue(((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
								? _livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getBaseValue()
								: 0) + finalGrow * 0.5));
		}
	}
}
