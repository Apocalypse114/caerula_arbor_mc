package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.entity.RunvolutionWaveEntity;
import net.apocalypse.careunite.entity.GladiiaWhirlEntity;
import net.apocalypse.careunite.entity.GladiiaEntity;

import java.util.List;
import java.util.Comparator;

public class GladiiaWhirlTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double t = 0;
		double damage = 0;
		double d = 0;
		t = entity.tickCount;
		if (t >= 120) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
		if (t >= 111) {
			return;
		}
		damage = (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.9;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(18 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				d = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
				if (entityiterator instanceof GladiiaEntity) {
					continue;
				}
				if (entityiterator instanceof GladiiaWhirlEntity) {
					continue;
				}
				if (entityiterator instanceof RunvolutionWaveEntity) {
					continue;
				}
				if (!(entityiterator instanceof LivingEntity)) {
					if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:no_join_whirl")))) {
						continue;
					}
				}
				enemy = entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
				if (entityiterator instanceof Player || (entityiterator instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false)) {
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
					if (new Object() {
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
					if (!(enemy instanceof GladiiaEntity)) {
						continue;
					}
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:is_humanside")))) {
					if (!(enemy instanceof GladiiaEntity)) {
						continue;
					}
				}
				if (d <= 9) {
					TurnRoundsProcedure.execute(entityiterator, entity);
				}
			}
		}
		if (t % 20 == 11) {
			if (!world.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_skill_rim")), SoundSource.NEUTRAL, 3, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_skill_rim")), SoundSource.NEUTRAL, 3, 1, false);
					}
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(24 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					d = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
					if (!(entityiterator instanceof LivingEntity)) {
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:no_join_whirl")))) {
							continue;
						}
					}
					if (entityiterator instanceof GladiiaEntity) {
						continue;
					}
					if (entityiterator instanceof GladiiaWhirlEntity) {
						continue;
					}
					enemy = entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
					if (entityiterator instanceof Player || (entityiterator instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false)) {
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
						if (new Object() {
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
						if (!(enemy instanceof GladiiaEntity)) {
							continue;
						}
					}
					if (d <= 12) {
						if (IsValidEnemyForIreneProcedure.execute(entityiterator, entity)) {
							entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:gladiia_magic")))),
									(float) damage);
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1, false, false));
						}
						if (d >= 3) {
							PullToGladiiaProcedure.execute(entityiterator, entity);
						}
					}
				}
			}
		}
	}
}
