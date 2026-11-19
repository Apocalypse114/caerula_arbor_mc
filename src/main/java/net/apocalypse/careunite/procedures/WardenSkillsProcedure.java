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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedWardenEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class WardenSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double perc = 0;
		double dura = 0;
		double gap = 0;
		if (!entity.isAlive()) {
			return;
		}
		if (entity.tickCount % 100 == 0) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(24 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
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
					}.checkGamemode(entityiterator) || new Object() {
						public boolean checkGamemode(Entity _ent) {
							if (_ent instanceof ServerPlayer _serverPlayer) {
								return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
							} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
								return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
										&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
							}
							return false;
						}
					}.checkGamemode(entityiterator)) {
						continue;
					}
					if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
						continue;
					}
					if (!(entityiterator instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(MobEffects.DARKNESS))) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 1200, 0, false, false));
					}
				}
			}
		}
		sklp1 = entity instanceof OceanizedWardenEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWardenEntity.DATA_skillp1) : 0;
		sklp2 = entity instanceof OceanizedWardenEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWardenEntity.DATA_skillp2) : 0;
		dura = entity instanceof OceanizedWardenEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWardenEntity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof OceanizedWardenEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWardenEntity.DATA_duration, (int) (dura - 1));
		}
		gap = 40;
		if (sklp1 > 0) {
			if (entity instanceof OceanizedWardenEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWardenEntity.DATA_skillp1, (int) (sklp1 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if (!((enemy != null ? entity.distanceTo(enemy) : -1) > 32 || dura > 0)) {
					if (entity instanceof OceanizedWardenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedWardenEntity.DATA_duration, 45);
					if (entity instanceof OceanizedWardenEntity) {
						((OceanizedWardenEntity) entity).setAnimation("animation.oceanized_warden.sonic");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 45, 0, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 45, 9, false, false));
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
					CaerulaArborMod.queueServerWork(30, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_boom")), SoundSource.HOSTILE, 2, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_boom")), SoundSource.HOSTILE, 2, 1, false);
								}
							}
							WardenSonicBoomProcedure.execute(world, entity, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null);
						}
					});
					if (entity instanceof OceanizedWardenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedWardenEntity.DATA_skillp1, 200);
				}
			}
		}
		if (sklp2 > 0) {
			if (entity instanceof OceanizedWardenEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWardenEntity.DATA_skillp2, (int) (sklp2 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if (!((enemy != null ? entity.distanceTo(enemy) : -1) > 4 || dura > 0)) {
					if (entity instanceof OceanizedWardenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedWardenEntity.DATA_duration, 45);
					if (entity instanceof OceanizedWardenEntity) {
						((OceanizedWardenEntity) entity).setAnimation("animation.oceanized_warden.combo");
					}
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
					CaerulaArborMod.queueServerWork(12, () -> {
						WardenThrowFlyProcedure.execute(world, entity);
					});
					CaerulaArborMod.queueServerWork(20, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, 2, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, 2, 1, false);
							}
						}
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 25, 9, false, false));
					});
					CaerulaArborMod.queueServerWork(27, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_boom")), SoundSource.HOSTILE, 2, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_boom")), SoundSource.HOSTILE, 2, 1, false);
							}
						}
						WardenLightBoomProcedure.execute(world, entity, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null);
					});
					if (entity instanceof OceanizedWardenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedWardenEntity.DATA_skillp2, 300);
				}
			}
		}
		if (sklp1 < 60) {
			gap = 10;
		} else if (sklp1 < 100) {
			gap = 20;
		}
		if (sklp2 < 60) {
			gap = 10;
		} else if (sklp2 < 100) {
			gap = 20;
		}
		if (gap > 0 && !(enemy == null) && enemy.isAlive()) {
			if (entity.tickCount % gap == 0) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.heartbeat")), SoundSource.HOSTILE, 2, Mth.nextInt(RandomSource.create(), (int) 0.9, (int) 1.05));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.heartbeat")), SoundSource.HOSTILE, 2, Mth.nextInt(RandomSource.create(), (int) 0.9, (int) 1.05), false);
					}
				}
			}
		}
	}
}
