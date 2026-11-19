package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.SonsEntity;
import net.apocalypse.careunite.entity.BishopFishEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class BishopTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double skl = 0;
		double end = 0;
		double smm = 0;
		double dx = 0;
		double rate = 0;
		double dz = 0;
		double d = 0;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.67) {
			if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.ANGER_OF_BISHOP.get()))) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.33) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ANGER_OF_BISHOP.get(), 20, 1));
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ANGER_OF_BISHOP.get(), 20, 0));
				}
			}
		}
		skl = entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_sklp) : 0;
		smm = entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_summonp) : 0;
		end = entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_endp) : 0;
		d = entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_duration) : 0;
		if (d > 0) {
			if (entity instanceof BishopFishEntity _datEntSetI)
				_datEntSetI.getEntityData().set(BishopFishEntity.DATA_duration, (int) (d - 1));
		}
		if (skl > 0) {
			if (entity instanceof BishopFishEntity _datEntSetI)
				_datEntSetI.getEntityData().set(BishopFishEntity.DATA_sklp, (int) (skl - 1));
		}
		if (smm > 0) {
			if (entity instanceof BishopFishEntity _datEntSetI)
				_datEntSetI.getEntityData().set(BishopFishEntity.DATA_summonp, (int) (smm - 1));
		}
		if (end <= 0) {
			if (GetSeabornAroundProcedure.execute(world, x, y, z, entity) < 32) {
				if (entity instanceof BishopFishEntity) {
					((BishopFishEntity) entity).setAnimation("animation.bishop.blast");
				}
				if (entity instanceof BishopFishEntity _datEntSetI)
					_datEntSetI.getEntityData().set(BishopFishEntity.DATA_duration, (int) (d + 40));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bishopfish_blast")), SoundSource.HOSTILE, 4, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bishopfish_blast")), SoundSource.HOSTILE, 4, 1, false);
					}
				}
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 40, 0));
				if (entity instanceof BishopFishEntity _datEntSetI)
					_datEntSetI.getEntityData().set(BishopFishEntity.DATA_endp, 2400);
				new Object() {
					void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
						BishopEndskillProcedure.execute(world, x, y, z, entity);
						for (Entity entityiterator : world.getEntities(entity, new AABB((x + 18), y, (z + 18), (x - 18), (y + 12), (z - 18)))) {
							if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity) && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 20) {
								entityiterator.hurt(
										new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), entity),
										(float) ((entity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity20.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 3));
							}
						}
						final int tick2 = ticks;
						CaerulaArborMod.queueServerWork(tick2, () -> {
							if (timedlooptotal > timedloopiterator + 1) {
								timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
							}
						});
					}
				}.timedLoop(0, 16, 5);
			}
		} else {
			if (entity instanceof BishopFishEntity _datEntSetI)
				_datEntSetI.getEntityData().set(BishopFishEntity.DATA_endp, (int) (end - 1));
		}
		if (entity.tickCount % 10 == 0 && new Vec3(x, y, z).distanceTo(new Vec3((entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_locx) : 0),
				(entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_locy) : 0), (entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_locz) : 0))) >= 3) {
			entity.setDeltaMovement(new Vec3(0, 0, 0));
			{
				Entity _ent = entity;
				_ent.teleportTo((entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_locx) : 0),
						(entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_locy) : 0), (entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_locz) : 0));
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport((entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_locx) : 0),
							(entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_locy) : 0), (entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_locz) : 0),
							_ent.getYRot(), _ent.getXRot());
			}
		}
		for (Entity entityiterator : world.getEntities(entity, new AABB((x - 6), (y - 6), (z - 6), (x + 6), (y + 6), (z + 6)))) {
			if (entityiterator instanceof SonsEntity) {
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 6) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD)), 99999);
				}
			}
		}
	}
}
