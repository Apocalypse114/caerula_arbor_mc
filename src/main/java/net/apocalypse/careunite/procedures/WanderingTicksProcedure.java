package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;

public class WanderingTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double cooldown = 0;
		double phase = 0;
		double curAction = 0;
		double actionT = 0;
		double nextAction = 0;
		double invis = 0;
		double trdT = 0;
		boolean isFighting = false;
		invis = entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_INVISIBLE_TICK) : 0;
		if (invis == 300) {
			if (entity instanceof OceanizedWanderingTraderEntity animatable)
				animatable.setTexture("wandering_trader_empty");
		} else if (invis == 1) {
			if (entity instanceof OceanizedWanderingTraderEntity animatable)
				animatable.setTexture("wandering_trader");
		}
		if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_INVISIBLE_TICK, (int) (invis - 1));
		if (entity.isAlive()) {
			phase = entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_PHASE) : 0;
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			actionT = entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_ACTION_TICK) : 0;
			curAction = entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_CURRENT_ACTION) : 0;
			if (actionT > 0) {
				if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_ACTION_TICK, (int) (actionT - 1));
			}
			if (phase == 0) {
				if (actionT > 0) {
					trdT = entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_TRADE_TICK) : 0;
					if (trdT > 0) {
						if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
							_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_TRADE_TICK, (int) (trdT - 1));
						if (trdT == 4) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.trade")), SoundSource.HOSTILE, 1,
											(float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15));
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.trade")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15),
											false);
								}
							}
							if (world instanceof ServerLevel _level) {
								ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.TRADER_BOX.get()));
								entityToSpawn.setPickUpDelay(5);
								entityToSpawn.setUnlimitedLifetime();
								_level.addFreshEntity(entityToSpawn);
							}
						}
					} else {
						if (curAction == 0) {
							if (actionT == 15) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.pickup")), SoundSource.HOSTILE, 1,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.pickup")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
									}
								}
							}
							if (actionT == 7 || actionT == 10) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.HOSTILE, 1,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
									}
								}
								EatEnchComplChitinAppleProcedure.execute(entity);
							}
						} else if (curAction == 1) {
							if (actionT == 16) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.pickup")), SoundSource.HOSTILE, 1,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.pickup")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
									}
								}
							}
							if (actionT == 10) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.drink_potion")), SoundSource.HOSTILE, 1,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.drink_potion")), SoundSource.HOSTILE, 1,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
									}
								}
							}
							if (actionT == 5) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.drink_potion")), SoundSource.HOSTILE, 1,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.drink_potion")), SoundSource.HOSTILE, 1,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
									}
								}
								if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 300, 79, false, false));
								if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
									_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_INVISIBLE_TICK, 300);
							}
						} else if (curAction == 2) {
							if (actionT == 17) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.return")), SoundSource.HOSTILE, 1,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.return")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
									}
								}
							}
							if (actionT == 8) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.thunder")), SoundSource.HOSTILE, 1,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.thunder")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
									}
								}
								LightnNearestLlamaProcedure.execute(world, x, y, z, entity);
							}
						} else if (curAction == 3) {
							if (actionT == 6) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.reappeared")), SoundSource.HOSTILE, (float) 1.5,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.reappeared")), SoundSource.HOSTILE, (float) 1.5,
												(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
									}
								}
								SummonMyLlamaProcedure.execute(world, x, y, z, entity);
							}
						} else {
							if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
								_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_ACTION_TICK, 0);
						}
					}
				} else {
					cooldown = entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_ACTION_COOLDOWN) : 0;
					isFighting = !(enemy == null) && enemy.isAlive();
					if (!isFighting && entity.tickCount % 10 == 5) {
						WanderingshowBoardProcedure.execute(world, x, y, z, entity);
					}
					if (cooldown > 0) {
						if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
							_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_ACTION_COOLDOWN, (int) (cooldown - 1));
					} else if (isFighting) {
						if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
							_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_ACTION_COOLDOWN, 160);
						nextAction = GenNextActionProcedure.execute(entity, curAction);
						if (nextAction > -0.5) {
							if (nextAction == 0) {
								actionT = 20;
							} else if (nextAction == 1) {
								actionT = 20;
							} else if (nextAction == 2) {
								actionT = 30;
							} else {
								actionT = 15;
							}
							if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
								_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_CURRENT_ACTION, (int) nextAction);
							if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
								_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_ACTION_TICK, (int) actionT);
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) actionT, 9, false, false));
						}
					}
				}
			}
		}
	}
}
