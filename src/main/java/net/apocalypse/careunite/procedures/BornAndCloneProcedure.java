package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class BornAndCloneProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double coef = 0;
		double coef_cur = 0;
		double attack_index = 0;
		double percentage = 0;
		double armor_index = 0;
		double n = 0;
		double health_index = 0;
		if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.POWER_OF_ANCHOR.get()))) {
			if (CaerulaArborModVariables.MapVariables.get(world).strategy_breed > 0) {
				if (!entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring"))) && !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanspawn")))
						&& !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanpet")))) {
					if (GetFellowAroundProcedure.execute(world, x, y, z, entity) < 5) {
						if (Math.random() < 0.05 + 0.05 * CaerulaArborModVariables.MapVariables.get(world).strategy_breed) {
							SummonAFellowProcedure.execute(entity);
						}
						if (GetFellowAroundProcedure.execute(world, x, y, z, entity) < 5) {
							if (CaerulaArborModVariables.MapVariables.get(world).strategy_breed >= 3) {
								if (Math.random() < 0.05 * (CaerulaArborModVariables.MapVariables.get(world).strategy_breed - 2)) {
									SummonAFellowProcedure.execute(entity);
								}
							}
						}
					}
				}
			}
		}
	}
}
