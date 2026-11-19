package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.SonsEntity;
import net.apocalypse.careunite.entity.BishopFishEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class BishopHurtProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double rate = 0;
		double sklp = 0;
		double dx = 0;
		double dz = 0;
		double yfnl = 0;
		if (entity.isAlive()) {
			if ((entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_sklp) : 0) <= 0) {
				if (entity instanceof BishopFishEntity) {
					((BishopFishEntity) entity).setAnimation("animation.bishop.skill");
				}
				if (entity instanceof BishopFishEntity _datEntSetI)
					_datEntSetI.getEntityData().set(BishopFishEntity.DATA_duration, (int) ((entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_duration) : 0) + 20));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bishopfish_flap")), SoundSource.HOSTILE, 3, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bishopfish_flap")), SoundSource.HOSTILE, 3, 1, false);
					}
				}
				new Object() {
					void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
						for (int index0 = 0; index0 < 180; index0++) {
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, (x + timedloopiterator * 2 * Math.sin(Math.toRadians(2 * index0))), (y + 0.5), (z + timedloopiterator * 2 * Math.cos(Math.toRadians(2 * index0))), 16, 0.15, 0.5, 0.15,
										0.1);
						}
						final int tick2 = ticks;
						CaerulaArborMod.queueServerWork(tick2, () -> {
							if (timedlooptotal > timedloopiterator + 1) {
								timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
							}
						});
					}
				}.timedLoop(0, 10, 2);
				for (Entity entityiterator : world.getEntities(entity, new AABB((x + 20), (y - 4), (z + 20), (x - 20), (y + 8), (z - 20)))) {
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
					if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity) && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 20) {
						dx = entityiterator.getX() - entity.getX();
						if ((dx) > (0) && (dx) < (1)) {
							dx = 1;
						} else if ((dx) > ((-1)) && (dx) < (0)) {
							dx = -1;
						} else if (dx == 0) {
							dx = 1;
						}
						dz = entityiterator.getZ() - entity.getZ();
						if ((dz) > (0) && (dz) < (1)) {
							dz = 1;
						} else if ((dz) > ((-1)) && (dz) < (0)) {
							dz = -1;
						} else if (dz == 0) {
							dz = 1;
						}
						entityiterator.push((1.5 / dx), 0.25, (1.5 / dz));
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic")))),
								(float) ((entity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity21.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1.5));
						DeductPlayerSanityProcedure.execute(entityiterator,
								(entity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity24.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
										* (entity instanceof LivingEntity _livingEntity25 && _livingEntity25.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
												? _livingEntity25.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getValue()
												: 0)
										* 1.5);
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 60, 0, false, false));
					}
				}
				if (entity instanceof LivingEntity _livEnt28 && _livEnt28.hasEffect(CaerulaArborModMobEffects.ANGER_OF_BISHOP.get())) {
					if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.ANGER_OF_BISHOP.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.ANGER_OF_BISHOP.get()).getAmplifier() : 0) >= 1) {
						if (entity instanceof BishopFishEntity _datEntSetI)
							_datEntSetI.getEntityData().set(BishopFishEntity.DATA_sklp, 100);
					} else {
						if (entity instanceof BishopFishEntity _datEntSetI)
							_datEntSetI.getEntityData().set(BishopFishEntity.DATA_sklp, 300);
					}
				} else {
					if (entity instanceof BishopFishEntity _datEntSetI)
						_datEntSetI.getEntityData().set(BishopFishEntity.DATA_sklp, 500);
				}
			}
			if ((entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_summonp) : 0) <= 0) {
				for (Entity entityiterator : world.getEntities(entity, new AABB((x - 32), (y - 16), (z - 32), (x + 32), (y + 16), (z + 32)))) {
					if (entityiterator instanceof SonsEntity) {
						if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 6) {
							entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD)), 99999);
						} else {
							rate = rate + 1;
						}
					}
				}
				if (rate < 32) {
					for (int index1 = 0; index1 < 8; index1++) {
						dx = Mth.nextDouble(RandomSource.create(), -22, 22);
						dz = Mth.nextDouble(RandomSource.create(), -22, 22);
						yfnl = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) (x + dx), (int) (z + dz));
						if (yfnl > y + 3) {
							yfnl = y + 3;
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = CaerulaArborModEntities.SONS.get().spawn(_level, BlockPos.containing(x + dx, yfnl, z + dz), MobSpawnType.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
							}
						}
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.SMOKE, (x + dx), (yfnl + 0.5), (z + dz), 16, 0.5, 0.5, 0.5, 0.2);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x + dx, yfnl, z + dz), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.guardian.flop")), SoundSource.HOSTILE, 1, 1);
							} else {
								_level.playLocalSound((x + dx), yfnl, (z + dz), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.guardian.flop")), SoundSource.HOSTILE, 1, 1, false);
							}
						}
					}
					if (entity instanceof LivingEntity _livEnt45 && _livEnt45.hasEffect(CaerulaArborModMobEffects.ANGER_OF_BISHOP.get())) {
						if (entity instanceof BishopFishEntity _datEntSetI)
							_datEntSetI.getEntityData().set(BishopFishEntity.DATA_summonp, 360);
					} else {
						if (entity instanceof BishopFishEntity _datEntSetI)
							_datEntSetI.getEntityData().set(BishopFishEntity.DATA_summonp, 600);
					}
				}
			}
		}
	}
}
