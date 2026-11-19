package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class ThirsterSpitProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double num = 0;
		double tX = 0;
		double tZ = 0;
		double tY = 0;
		num = 2;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:thirster_spit")), SoundSource.HOSTILE, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:thirster_spit")), SoundSource.HOSTILE, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
			}
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
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
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 20) {
					num = num - 1;
					tX = tX + entityiterator.getX();
					tZ = tZ + entityiterator.getZ();
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.MOIST_BOOM.get()), (entityiterator.getX()), (entityiterator.getY() + 0.75), (entityiterator.getZ()), 8, 0.75, 0.75, 0.75, 0.1);
					CaerulaArborMod.queueServerWork(15, () -> {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), entity),
								(float) (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity16.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					});
					if (num <= 0) {
						break;
					}
				}
			}
		}
		if (num < 2) {
			tX = tX / (2 - num);
			tZ = tZ / (2 - num);
			tY = FindYzforTearProcedure.execute(world, tX, y, tZ);
			if (tY < 1111) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.ABSORBER_LIMB.get().spawn(_level, BlockPos.containing(tX, tY, tZ), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			}
		}
	}
}
