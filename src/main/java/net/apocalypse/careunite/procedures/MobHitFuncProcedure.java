package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.SpikeChestEntity;
import net.apocalypse.careunite.entity.HighmoreEntity;
import net.apocalypse.careunite.entity.GuideAbyssalEntity;
import net.apocalypse.careunite.entity.FakeOffspringEntity;
import net.apocalypse.careunite.entity.ComplexChitinGolemEntity;
import net.apocalypse.careunite.entity.ChitinGolemEntity;
import net.apocalypse.careunite.entity.BoneFishEntity;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MobHitFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		boolean once = false;
		double amplifi = 0;
		double dx = 0;
		double dz = 0;
		double limithard = 0;
		double dy = 0;
		double rate = 0;
		double sklp = 0;
		if (!event.isCanceled()) {
			if (IsAlchemyProcedure.execute(sourceentity)) {
				if (!IsValidEnemyForAlchemyProcedure.execute(entity, sourceentity)) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
				}
			}
			if (IsSeabornProcedure.execute(entity) && !IsSeabornProcedure.execute(sourceentity) && !IsSeabornPetProcedure.execute(entity) && !IsSkilMigrationProcedure.execute(entity)) {
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_migration > 0
						&& (sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization < 3) {
					if (!(new Object() {
						public boolean checkGamemode(Entity _ent) {
							if (_ent instanceof ServerPlayer _serverPlayer) {
								return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
							} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
								return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
										&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
							}
							return false;
						}
					}.checkGamemode(sourceentity)) && !damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:bypasses_migration")))) {
						for (Entity entityiterator : world.getEntities(entity,
								new AABB((x - (8 + CaerulaArborModVariables.MapVariables.get(world).strategy_migration * 16)), (y - 16), (z - (8 + CaerulaArborModVariables.MapVariables.get(world).strategy_migration * 16)),
										(x + 8 + CaerulaArborModVariables.MapVariables.get(world).strategy_migration * 24), (y + 16), (z + 8 + CaerulaArborModVariables.MapVariables.get(world).strategy_migration * 24)))) {
							if (IsSeabornProcedure.execute(entityiterator) && !IsSeabornPetProcedure.execute(entityiterator)) {
								if (entityiterator == sourceentity) {
									continue;
								}
								if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) && (entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).isAlive()) {
									continue;
								}
								if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:ignore_migration")))) {
									continue;
								}
								if (entityiterator instanceof Mob _entity)
									_entity.getNavigation().moveTo(x, y, z, 1);
								if (entityiterator instanceof Mob _entity && sourceentity instanceof LivingEntity _ent)
									_entity.setTarget(_ent);
							}
						}
					}
				}
			}
			if (entity instanceof Player && (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization >= 3) {
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_migration > 0 && !IsSeabornProcedure.execute(sourceentity)) {
					for (Entity entityiterator : world.getEntities(entity,
							new AABB((x - (8 + CaerulaArborModVariables.MapVariables.get(world).strategy_migration * 24)), (y - 16), (z - (8 + CaerulaArborModVariables.MapVariables.get(world).strategy_migration * 24)),
									(x + 8 + CaerulaArborModVariables.MapVariables.get(world).strategy_migration * 24), (y + 16), (z + 8 + CaerulaArborModVariables.MapVariables.get(world).strategy_migration * 24)))) {
						if (IsSeabornProcedure.execute(entityiterator) && !IsSeabornPetProcedure.execute(entityiterator)) {
							if (entityiterator == sourceentity) {
								continue;
							}
							if (entityiterator instanceof Mob _entity)
								_entity.getNavigation().moveTo(x, y, z, 0.8);
							if (entityiterator instanceof Mob _entity && sourceentity instanceof LivingEntity _ent)
								_entity.setTarget(_ent);
						}
					}
				}
			}
			if (IsSeabornProcedure.execute(sourceentity)) {
				if (world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.NATURAL_EVOLUTION)) {
					if (!world.isClientSide()) {
						CaerulaArborModVariables.MapVariables.get(world).evo_point_grow = CaerulaArborModVariables.MapVariables.get(world).evo_point_grow + amount * 0.025;
						CaerulaArborModVariables.MapVariables.get(world).syncData(world);
						UpgradeGrowProcedure.execute(world);
						UpgradeSilenceProcedure.execute(world, amount * 0.025);
						UpgradeSublimationProcedure.execute(world, GetPointFromHeightProcedure.execute(sourceentity, amount * 0.025));
					}
				}
			}
			if (!world.isClientSide()) {
				if (IsSeabornProcedure.execute(entity)) {
					if (sourceentity instanceof ServerPlayer _player) {
						Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:encounter_from_the_ocean"));
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
					if (world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.NATURAL_EVOLUTION) && !damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:bypasses_evolution")))) {
						CaerulaArborModVariables.MapVariables.get(world).evo_point_subsisting = CaerulaArborModVariables.MapVariables.get(world).evo_point_subsisting
								+ Math.min(amount, entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.025;
						CaerulaArborModVariables.MapVariables.get(world).syncData(world);
						UpgradeSubsisProcedure.execute(world);
						UpgradeSilenceProcedure.execute(world, Math.min(amount, entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.025);
						UpgradeSublimationProcedure.execute(world, GetPointFromHeightProcedure.execute(entity, Math.min(amount, entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.025));
					}
					if (IsSeabornProcedure.execute(sourceentity)) {
						if (!IsValidEnemyForSeabornProcedure.execute(entity, sourceentity)) {
							if (event != null && event.isCancelable()) {
								event.setCanceled(true);
							}
						}
					}
					if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting >= 3 && Math.random() < 0.5) {
						if (ModGriefSettingsProcedure.execute(world)) {
							if (entity.isAlive() && !(entity instanceof GuideAbyssalEntity)) {
								if (!entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanspawn"))) && DetectForTrailProcedure.execute(world, x, y, z)) {
									new Object() {
										void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
											if (world instanceof Level _level) {
												if (!_level.isClientSide()) {
													_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, 1, 1);
												} else {
													_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, 1, 1, false);
												}
											}
											final int tick2 = ticks;
											CaerulaArborMod.queueServerWork(tick2, () -> {
												if (timedlooptotal > timedloopiterator + 1) {
													timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
												}
											});
										}
									}.timedLoop(0, 3, 5);
									if (entity instanceof LivingEntity _entity)
										_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 8));
								}
							}
						}
					}
					if (!(entity instanceof LivingEntity _livEnt33 && _livEnt33.hasEffect(CaerulaArborModMobEffects.POWER_OF_ANCHOR.get())) && !(entity instanceof LivingEntity _livEnt34 && _livEnt34.hasEffect(CaerulaArborModMobEffects.MUTE.get()))) {
						if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence > 0 && !entity.getPersistentData().getBoolean("immortalTriggered")) {
							if (!IsSeabornBossProcedure.execute(entity) && !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanspawn"))) && !IsSeabornPetProcedure.execute(entity)) {
								if (Math.random() < 0.01 * CaerulaArborModVariables.MapVariables.get(world).strategy_silence && !(sourceentity == entity)) {
									if (GetSeabornAroundProcedure.execute(world, x, y, z, entity) < Math.min((world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT)),
											(double) CaerulaConfigsConfiguration.CLONE_NUM.get())) {
										if (entity instanceof LivingEntity _livingEntity40 && _livingEntity40.getAttributes().hasAttribute(CaerulaArborModAttributes.SUMMONABLE.get())
												&& (entity instanceof LivingEntity _livingEntity41 && _livingEntity41.getAttributes().hasAttribute(CaerulaArborModAttributes.SUMMONABLE.get())
														? _livingEntity41.getAttribute(CaerulaArborModAttributes.SUMMONABLE.get()).getBaseValue()
														: 0) == 1) {
											SummonAFellowProcedure.execute(entity);
											if (world instanceof ServerLevel _level)
												_level.sendParticles(ParticleTypes.CLOUD, x, y, z, 64, 1.5, 2, 1.5, 0.1);
											if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 3) {
												if (Math.random() < 0.5) {
													SummonDecayedEliteFishProcedure.execute(world, x, y, z);
												}
											}
											if (entity instanceof LivingEntity _livingEntity43 && _livingEntity43.getAttributes().hasAttribute(CaerulaArborModAttributes.SUMMONABLE.get()))
												_livingEntity43.getAttribute(CaerulaArborModAttributes.SUMMONABLE.get()).setBaseValue(0);
										}
									}
								}
							}
							if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 3) {
								if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 10 && !damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:no_beat_back")))) {
									if (Math.random() < -0.3 + CaerulaArborModVariables.MapVariables.get(world).strategy_silence * 0.15) {
										if (entity instanceof Player) {
											sourceentity.hurt(
													new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_counter")))),
													(float) ((entity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE)
															? _livingEntity47.getAttribute(Attributes.ATTACK_DAMAGE).getValue()
															: 0) * 0.1));
										} else {
											sourceentity.hurt(
													new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_counter")))),
													(float) ((entity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE)
															? _livingEntity50.getAttribute(Attributes.ATTACK_DAMAGE).getValue()
															: 0) * 0.5));
										}
									}
								}
							}
						}
					}
				}
				if (sourceentity instanceof BoneFishEntity) {
					GiveLessArmorProcedure.execute(entity, 1);
				}
				if (sourceentity instanceof FakeOffspringEntity) {
					GiveLessArmorProcedure.execute(entity, 2);
				}
				if (sourceentity instanceof ChitinGolemEntity || sourceentity instanceof ComplexChitinGolemEntity) {
					if (entity.getBbWidth() * entity.getBbHeight() <= 6 && !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:golem_attack")))) {
						entity.push(0, 0.5, 0);
					}
				}
				if (sourceentity instanceof HighmoreEntity) {
					if (!(sourceentity == entity)) {
						GiveLessArmorProcedure.execute(entity, 21);
					}
				}
				if (entity instanceof SpikeChestEntity) {
					if (entity.isAlive()) {
						sourceentity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:chest_spike")))),
								(float) (amount * 0.33));
					}
				}
				if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:inquisition")))
						&& sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:inquisition")))) {
					if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == sourceentity || (sourceentity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity)) {
						if (event != null && event.isCancelable()) {
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}
}
