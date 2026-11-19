package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.MoistDragonBreathEntity;

import java.util.UUID;
import java.util.List;
import java.util.Comparator;

public class DragonBreathExplodeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity owner = null;
		Entity enemy = null;
		String uuid = "";
		String uuid1 = "";
		double d = 0;
		double T = 0;
		double thita = 0;
		boolean isFromPlayer = false;
		if (world.isClientSide()) {
			return;
		}
		d = entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		uuid = entity instanceof MoistDragonBreathEntity _datEntS ? _datEntS.getEntityData().get(MoistDragonBreathEntity.DATA_TARGET) : "";
		uuid1 = entity instanceof MoistDragonBreathEntity _datEntS ? _datEntS.getEntityData().get(MoistDragonBreathEntity.DATA_OWNER) : "";
		enemy = new Object() {
			Entity entityFromStringUUID(String uuid, Level world) {
				Entity _uuidentity = null;
				if (world instanceof ServerLevel _server) {
					try {
						_uuidentity = _server.getEntity(UUID.fromString(uuid));
					} catch (Exception e) {
					}
				}
				return _uuidentity;
			}
		}.entityFromStringUUID(uuid, (Level) world);
		owner = new Object() {
			Entity entityFromStringUUID(String uuid, Level world) {
				Entity _uuidentity = null;
				if (world instanceof ServerLevel _server) {
					try {
						_uuidentity = _server.getEntity(UUID.fromString(uuid));
					} catch (Exception e) {
					}
				}
				return _uuidentity;
			}
		}.entityFromStringUUID(uuid1, (Level) world);
		if (!(owner == null) && owner.isAlive()) {
			d = owner instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity8.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		} else {
			d = entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		}
		if (d > 0) {
			T = entity instanceof MoistDragonBreathEntity _datEntI ? _datEntI.getEntityData().get(MoistDragonBreathEntity.DATA_TYPE) : 0;
			if (T > 0.5) {
				d = d * 1.35;
			}
			isFromPlayer = owner instanceof Player;
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (!(entityiterator instanceof LivingEntity)) {
						continue;
					}
					if (entityiterator == owner) {
						continue;
					}
					if (CheckBreathTargetProcedure.execute(entityiterator, enemy, owner)) {
						if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 2.5) {
							entityiterator.hurt(
									new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), entity, owner),
									(float) d);
							DeductPlayerSanityProcedure.execute(entityiterator, d * 20);
							if (isFromPlayer) {
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.TRAPPED.get(), 60, 0, false, false));
							}
						}
					}
				}
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_explode")), SoundSource.HOSTILE, 3, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_explode")), SoundSource.HOSTILE, 3, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.DRAGON_BREATH, x, (y + 0.25), z, 32, 2, 2, 2, 0.18);
		}
		if (!entity.level().isClientSide())
			entity.discard();
	}
}
