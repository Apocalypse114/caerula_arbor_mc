package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.AbsorberLimbEntity;

public class OnSublimationTickProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double subl = 0;
		double subs = 0;
		double finalSubs = 0;
		double notHurtTick = 0;
		double migra = 0;
		double finalMigra = 0;
		double maxHealth = 0;
		boolean isElite = false;
		boolean isTiny = false;
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
			return;
		}
		if (!world.isClientSide()) {
			subl = CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation;
			subs = CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting;
			migra = CaerulaArborModVariables.MapVariables.get(world).strategy_migration;
			finalSubs = Math.min(subs, subl);
			finalMigra = Math.min(migra, subl);
			notHurtTick = entity.tickCount - entity.getPersistentData().getDouble("caerula.lastHurtByTime");
			maxHealth = entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
			if (notHurtTick >= 220 - 20 * finalSubs) {
				if (finalSubs > 0) {
					if (!(entity instanceof AbsorberLimbEntity)) {
						LivingHeal1Procedure.execute(entity, maxHealth * finalSubs * 0.01 * 0.05);
					}
					if (notHurtTick == 220 - 20 * finalSubs) {
						if (finalSubs == 3) {
							if (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()))
								_livingEntity7.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
										.setBaseValue(Math.max(entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
												? _livingEntity6.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()).getBaseValue()
												: 0, 1));
						} else if (finalSubs == 4) {
							if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()))
								_livingEntity9.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
										.setBaseValue(Math.max(entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
												? _livingEntity8.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()).getBaseValue()
												: 0, 2));
						}
					}
				}
				if (finalMigra > 0) {
					if ((entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get())
							? _livingEntity10.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()).getBaseValue()
							: 0) < maxHealth * 2) {
						AddLivingBarrierProcedure.execute(entity, maxHealth * (0.01 + 0.005 * finalMigra) * 0.05);
					}
				}
			}
			if (!entity.getPersistentData().getBoolean("sublimationBlessed") && finalMigra >= 3 && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < maxHealth * 0.3) {
				isElite = entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanelite")));
				isTiny = entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:tiny_seaborn")));
				entity.getPersistentData().putBoolean("sublimationBlessed", true);
				if (!isTiny) {
					if (finalMigra == 3) {
						if (isElite) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IMMORTAL.get(), 100, 0, false, false));
						} else {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IMMORTAL.get(), 60, 0, false, false));
						}
					} else if (finalMigra == 4) {
						if (isElite) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IMMORTAL.get(), 200, 0, false, false));
						} else {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IMMORTAL.get(), 100, 0, false, false));
						}
					}
				}
			}
		}
	}
}
