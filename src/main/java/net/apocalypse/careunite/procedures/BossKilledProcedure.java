package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.RouteShaperEntity;
import net.apocalypse.careunite.entity.MartusEntity;
import net.apocalypse.careunite.entity.IzumikEntity;
import net.apocalypse.careunite.entity.HighmoreEntity;
import net.apocalypse.careunite.entity.CompassionPrayerEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class BossKilledProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!event.isCanceled()) {
			if (entity instanceof CompassionPrayerEntity) {
				if ((entity instanceof CompassionPrayerEntity _datEntI ? _datEntI.getEntityData().get(CompassionPrayerEntity.DATA_PHASE) : 0) == 0
						&& (entity instanceof CompassionPrayerEntity _datEntI ? _datEntI.getEntityData().get(CompassionPrayerEntity.DATA_REVIVE_TICK) : 0) <= 0) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
					if (entity instanceof CompassionPrayerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(CompassionPrayerEntity.DATA_REVIVE_TICK, 200);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 200, 1, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 200, 1, false, false));
				}
			}
			if (entity instanceof MartusEntity) {
				if ((entity instanceof MartusEntity _datEntI ? _datEntI.getEntityData().get(MartusEntity.DATA_phase) : 0) == 0) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(CaerulaArborModMobEffects.INVULNERABLE.get());
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 200, 1, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 200, 1, false, false));
					if (entity instanceof MartusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(MartusEntity.DATA_phase, 1);
					if (entity instanceof MartusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(MartusEntity.DATA_skillp1, 600);
					if (entity instanceof MartusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(MartusEntity.DATA_skillp2, 200);
				}
			}
			if (entity instanceof IzumikEntity) {
				if ((entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0) == 0) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.6));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 300, 1, false, false));
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_phase, 1);
					if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
						_livingEntity22.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
								.setBaseValue(((entity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
										? _livingEntity21.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
										: 0) + 2));
				} else if ((entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0) == 1 && CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 3) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 200, 1, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 200, 1, false, false));
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_phase, 2);
					if (entity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
						_livingEntity28.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
								.setBaseValue(((entity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
										? _livingEntity27.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
										: 0) + 2));
				} else {
					for (Entity entityiterator : new ArrayList<>(world.players())) {
						if ((entity.level().dimension()) == (entityiterator.level().dimension())) {
							if (entityiterator instanceof ServerPlayer _player) {
								Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:stella_caerula"));
								AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
								if (!_ap.isDone()) {
									for (String criteria : _ap.getRemainingCriteria())
										_player.getAdvancements().award(_adv, criteria);
								}
							}
						}
					}
				}
			}
			if (entity instanceof RouteShaperEntity) {
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting >= 4) {
					if ((entity instanceof RouteShaperEntity _datEntI ? _datEntI.getEntityData().get(RouteShaperEntity.DATA_phase) : 0) == 0) {
						if (event != null && event.isCancelable()) {
							event.setCanceled(true);
						}
						if (entity instanceof RouteShaperEntity _datEntSetI)
							_datEntSetI.getEntityData().set(RouteShaperEntity.DATA_phase, 1);
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 200, 1, false, false));
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 200, 1, false, false));
					}
				}
			}
			if (entity instanceof HighmoreEntity) {
				if ((entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0) == 0) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 400, 1, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 400, 0, false, false));
					CaerulaArborMod.queueServerWork(300, () -> {
						if (!(entity == null)) {
							if (entity instanceof HighmoreEntity _datEntSetI)
								_datEntSetI.getEntityData().set(HighmoreEntity.DATA_phase, 1);
						}
					});
				} else if ((entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0) == 1 && CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 3) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 200, 1, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 200, 1, false, false));
					CaerulaArborMod.queueServerWork(150, () -> {
						if (!(entity == null)) {
							if (entity instanceof HighmoreEntity _datEntSetI)
								_datEntSetI.getEntityData().set(HighmoreEntity.DATA_phase, 2);
						}
					});
				} else {
					for (Entity entityiterator : new ArrayList<>(world.players())) {
						if ((entity.level().dimension()) == (entityiterator.level().dimension())) {
							if (entityiterator instanceof ServerPlayer _player) {
								Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:precious_days"));
								AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
								if (!_ap.isDone()) {
									for (String criteria : _ap.getRemainingCriteria())
										_player.getAdvancements().award(_adv, criteria);
								}
							}
						}
					}
				}
			}
		}
	}
}
