package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.OceanizedEvokerEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class EvokerSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double dist = 0;
		if (entity.isAlive()) {
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			sklp1 = entity instanceof OceanizedEvokerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEvokerEntity.DATA_skillp1) : 0;
			sklp2 = entity instanceof OceanizedEvokerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEvokerEntity.DATA_skillp2) : 0;
			if (sklp1 <= 0) {
				if (!(enemy == null) && enemy.isAlive()) {
					dist = Math.round(enemy != null ? entity.distanceTo(enemy) : -1);
					if (dist <= 35) {
						if (entity instanceof OceanizedEvokerEntity) {
							((OceanizedEvokerEntity) entity).setAnimation("animation.oceanized_evoker.spell");
						}
						if (entity instanceof OceanizedEvokerEntity _datEntSetI)
							_datEntSetI.getEntityData().set(OceanizedEvokerEntity.DATA_skillp1, 100);
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 9, false, false));
						if (dist > 6) {
							new Object() {
								void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
									if (entity.isAlive()) {
										LinearFangsProcedure.execute(world, entity, timedloopiterator + 1);
									}
									final int tick2 = ticks;
									CaerulaArborMod.queueServerWork(tick2, () -> {
										if (timedlooptotal > timedloopiterator + 1) {
											timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
										}
									});
								}
							}.timedLoop(0, (int) dist, 1);
						} else {
							new Object() {
								void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
									if (entity.isAlive()) {
										RingFangsProcedure.execute(world, x, y, z, entity, (timedloopiterator + 1) * 3);
									}
									final int tick2 = ticks;
									CaerulaArborMod.queueServerWork(tick2, () -> {
										if (timedlooptotal > timedloopiterator + 1) {
											timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
										}
									});
								}
							}.timedLoop(0, 5, 2);
						}
					}
				}
			} else {
				if (entity instanceof OceanizedEvokerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedEvokerEntity.DATA_skillp1, (int) (sklp1 - 1));
				if (sklp1 == 20) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_attack")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_attack")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
				}
			}
			if (sklp2 <= 0) {
				if (!(enemy == null) && enemy.isAlive()) {
					if (entity instanceof OceanizedEvokerEntity) {
						((OceanizedEvokerEntity) entity).setAnimation("animation.oceanized_evoker.spell");
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
					if (entity instanceof OceanizedEvokerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedEvokerEntity.DATA_skillp2, 300);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 9, false, false));
					CaerulaArborMod.queueServerWork(10, () -> {
						new Object() {
							void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
								if (entity.isAlive()) {
									EvokeRandomFlyProcedure.execute(world, x, y, z, entity);
								}
								final int tick2 = ticks;
								CaerulaArborMod.queueServerWork(tick2, () -> {
									if (timedlooptotal > timedloopiterator + 1) {
										timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
									}
								});
							}
						}.timedLoop(0, 4, 5);
					});
				}
			} else {
				if (entity instanceof OceanizedEvokerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedEvokerEntity.DATA_skillp2, (int) (sklp2 - 1));
				if (sklp2 == 20) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.prepare_summon")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
				}
			}
			RaiderRideRavagerProcedure.execute(world, x, y, z, entity);
		}
	}
}
