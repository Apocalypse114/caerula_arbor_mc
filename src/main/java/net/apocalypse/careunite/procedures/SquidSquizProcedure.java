package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.apocalypse.careunite.entity.PromotedSquidEntity;
import net.apocalypse.careunite.entity.PromotedGlowSquidEntity;

import java.util.List;
import java.util.Comparator;

public class SquidSquizProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, boolean glow) {
		if (entity == null)
			return;
		Entity enemy = null;
		if (glow) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.GLOW_SQUID_INK, x, y, z, 32, 2, 2, 2, 0.1);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.glow_squid.squirt")), SoundSource.HOSTILE, (float) 2.5, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.glow_squid.squirt")), SoundSource.HOSTILE, (float) 2.5, 1, false);
				}
			}
		} else {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SQUID_INK, x, y, z, 32, 2, 2, 2, 0.1);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.squid.squirt")), SoundSource.HOSTILE, (float) 2.5, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.squid.squirt")), SoundSource.HOSTILE, (float) 2.5, 1, false);
				}
			}
		}
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof Squid) {
					continue;
				}
				if (entityiterator instanceof GlowSquid) {
					continue;
				}
				if (entityiterator instanceof PromotedSquidEntity) {
					continue;
				}
				if (entityiterator instanceof PromotedGlowSquidEntity) {
					continue;
				}
				if (IsSameTeamProcedure.execute(entity, entityiterator)) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if (!(entityiterator == enemy)) {
						continue;
					}
				}
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator instanceof Player) {
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
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 6) {
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, false, false));
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1, false, false));
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DEDUCT_ONE_SANITY.get(), 100, 1, false, false));
				}
			}
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SANITY_COUNTER_BACK.get(), 400, 0, false, false));
	}
}
