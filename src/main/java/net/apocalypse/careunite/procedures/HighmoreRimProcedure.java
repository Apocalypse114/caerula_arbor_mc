package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.HighmoreEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class HighmoreRimProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double range = 0;
		double lvl = 0;
		double sklp1 = 0;
		double sklp2 = 0;
		Entity eennnee = null;
		if ((entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0) == 0) {
			range = 7;
		} else if ((entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0) == 1) {
			range = 11;
			lvl = 1;
		} else {
			range = 17;
			lvl = 2;
		}
		sklp1 = entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_skillp1) : 0;
		sklp2 = entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_skillp2) : 0;
		if (entity.isAlive() && !(entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get()))) {
			for (int index0 = 0; index0 < 120; index0++) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.DOLPHIN, (x + range * Math.sin(Math.toRadians(3 * index0))), y, (z + range * Math.cos(Math.toRadians(3 * index0))), 6, 0.15, 0.2, 0.15, 0.1);
			}
			if (entity.tickCount % 20 == 10) {
				{
					final Vec3 _center = new Vec3(x, (y + 1.5), z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((range * 2) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator == entity) {
							continue;
						}
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
							if (!(entityiterator == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null))) {
								continue;
							}
						}
						if (new Object() {
							public boolean checkGamemode(Entity _ent) {
								if (_ent instanceof ServerPlayer _serverPlayer) {
									return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
								} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
									return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
											&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
								}
								return false;
							}
						}.checkGamemode(entityiterator)) {
							continue;
						}
						if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= range) {
							if (!(entityiterator instanceof LivingEntity _livEnt14 && _livEnt14.hasEffect(CaerulaArborModMobEffects.FADINGSHADOW.get()))) {
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FADINGSHADOW.get(), 20, (int) lvl, false, false));
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20, (int) lvl, false, true));
							}
						}
					}
				}
			}
			eennnee = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (sklp1 <= 0 && !(entity instanceof LivingEntity _livEnt19 && _livEnt19.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get()))) {
				if (!(null == eennnee) && eennnee.isAlive()) {
					if (entity instanceof HighmoreEntity) {
						((HighmoreEntity) entity).setAnimation("animation.highmore.skill");
					}
					if (!world.isClientSide()) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:highmore_preamble")), SoundSource.HOSTILE, 4, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:highmore_preamble")), SoundSource.HOSTILE, 4, 1, false);
							}
						}
					}
					if (lvl == 0) {
						CaerulaArborMod.queueServerWork(15, () -> {
							if (!(null == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null))) {
								HighmoreMutltiShootProcedure.execute(world, x, y, z, entity, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null, 3);
							}
						});
						if (entity instanceof HighmoreEntity _datEntSetI)
							_datEntSetI.getEntityData().set(HighmoreEntity.DATA_skillp1, 200);
					} else {
						CaerulaArborMod.queueServerWork(15, () -> {
							if (!(null == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null))) {
								HighmoreMutltiShootProcedure.execute(world, x, y, z, entity, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null, 5);
							}
						});
						if (entity instanceof HighmoreEntity _datEntSetI)
							_datEntSetI.getEntityData().set(HighmoreEntity.DATA_skillp1, 140);
					}
				}
			} else {
				if (entity instanceof HighmoreEntity _datEntSetI)
					_datEntSetI.getEntityData().set(HighmoreEntity.DATA_skillp1, (int) (sklp1 - 1));
			}
			if (sklp2 <= 0) {
				if (!(null == eennnee) && eennnee.isAlive()) {
					if (entity instanceof HighmoreEntity) {
						((HighmoreEntity) entity).setAnimation("animation.highmore.charge");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 80, 0, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 80, 0, false, false));
					CaerulaArborMod.queueServerWork(15, () -> {
						entity.push((Mth.nextDouble(RandomSource.create(), -0.3, 0.3)), (Mth.nextDouble(RandomSource.create(), 0, 0.3)), (Mth.nextDouble(RandomSource.create(), -0.3, 0.3)));
						new Object() {
							void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
								HighmoreRangedAttackProcedure.execute(world, x, y, z, entity);
								final int tick2 = ticks;
								CaerulaArborMod.queueServerWork(tick2, () -> {
									if (timedlooptotal > timedloopiterator + 1) {
										timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
									}
								});
							}
						}.timedLoop(0, 9, 3);
					});
					CaerulaArborMod.queueServerWork(65, () -> {
						HighmoreBlastProcedure.execute(world, x, y, z, entity);
					});
					if (lvl == 0) {
						if (entity instanceof HighmoreEntity _datEntSetI)
							_datEntSetI.getEntityData().set(HighmoreEntity.DATA_skillp2, 680);
					} else if (lvl == 1) {
						if (entity instanceof HighmoreEntity _datEntSetI)
							_datEntSetI.getEntityData().set(HighmoreEntity.DATA_skillp2, 600);
					} else {
						if (entity instanceof HighmoreEntity _datEntSetI)
							_datEntSetI.getEntityData().set(HighmoreEntity.DATA_skillp2, 520);
					}
				}
			} else {
				if (entity instanceof HighmoreEntity _datEntSetI)
					_datEntSetI.getEntityData().set(HighmoreEntity.DATA_skillp2, (int) (sklp2 - 1));
				if ((entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0) >= 2) {
					if (entity instanceof HighmoreEntity _datEntSetI)
						_datEntSetI.getEntityData().set(HighmoreEntity.DATA_skillp2, (int) (sklp2 - 1));
				}
			}
		}
	}
}
