package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerEvolutionTickFUncProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		double addDef = 0;
		double tickCount = 0;
		boolean lowerHealth = false;
		if (CanPlayerEvoProcedure.execute(entity)) {
			tickCount = entity.tickCount;
			if (IsNexusNoRejectionSelectedProcedure.execute(entity)) {
				if (HasDisoProcedure.execute(entity)) {
					{
						double _setval = -1;
						entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.disoclusion = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				}
			}
			if (IsNexusRegSanitySelectedProcedure.execute(entity)) {
				RevivesPlayerSanityProcedure.execute(entity, 1);
			}
			if (tickCount % 10 == 0) {
				if (IsNexusRegLightsSelectedProcedure.execute(entity)) {
					RevivesPlayerLightsProcedure.execute(entity, 0.1);
				}
				addDef = GetNodeAddDefProcedure.execute(entity);
				if (addDef > 0) {
					if (addDef == 1) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 20, 0, false, false));
					} else if (addDef == 2) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 20, 2, false, false));
					} else if (addDef == 3) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 20, 5, false, false));
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_PERCLY_TINY.get(), 20, 2, false, false));
					} else if (addDef == 4) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 20, 9, false, false));
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_PERCLY_TINY.get(), 20, 7, false, false));
					}
				}
				addDef = GetNodeAddResisProcedure.execute(entity);
				if (addDef > 0) {
					if (addDef == 1) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_RESIS_TINY.get(), 20, 0, false, false));
					} else if (addDef == 2) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_RESIS_TINY.get(), 20, 2, false, false));
					} else if (addDef == 3) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_RESIS_TINY.get(), 20, 5, false, false));
					} else if (addDef == 4) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_RESIS_TINY.get(), 20, 9, false, false));
					}
				}
				addDef = GetNodeAddSpeedProcedure.execute(entity);
				if (addDef > 0) {
					if (addDef == 1) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_SPEED_TINY.get(), 20, 0, false, false));
					} else if (addDef == 2) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_SPEED_TINY.get(), 20, 2, false, false));
					} else if (addDef == 3) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_SPEED_TINY.get(), 20, 5, false, false));
					} else if (addDef == 4) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_SPEED_TINY.get(), 20, 9, false, false));
					}
				}
				addDef = GetNodeAddSanityProcedure.execute(entity);
				if (addDef > 0) {
					if (addDef == 1) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REDUCE_SANITY_MODIFIER.get(), 20, 0, false, false));
					} else if (addDef == 2) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REDUCE_SANITY_MODIFIER.get(), 20, 2, false, false));
					} else if (addDef == 3) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REDUCE_SANITY_MODIFIER.get(), 20, 5, false, false));
					} else if (addDef == 4) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REDUCE_SANITY_MODIFIER.get(), 20, 9, false, false));
					}
				}
				addDef = GetNodeAddMissProcedure.execute(entity);
				lowerHealth = GetHealthPercProcedure.execute(entity) <= 0.5;
				if (addDef > 0) {
					if (addDef == 1) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 20, 2, false, false));
					} else if (addDef == 2) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 20, 7, false, false));
					} else if (addDef == 3) {
						if (lowerHealth) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 20, 22, false, false));
						} else {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 20, 14, false, false));
						}
					} else if (addDef == 4) {
						if (lowerHealth) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 20, 41, false, false));
						} else {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 20, 23, false, false));
						}
					}
				}
				addDef = GetNodeEutectesProcedure.execute(entity);
				if (addDef > 0) {
					if (!lowerHealth) {
						if (addDef == 1) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DAMAGE_TINY.get(), 20, 0, false, false));
						} else if (addDef == 2) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DAMAGE_TINY.get(), 20, 2, false, false));
						} else if (addDef == 3) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DAMAGE_TINY.get(), 20, 5, false, false));
						} else if (addDef == 4) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DAMAGE_TINY.get(), 20, 9, false, false));
						}
					}
				}
			}
		}
	}
}
