package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizedWitherEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class OceanWitherTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double spawn = 0;
		double skillp = 0;
		double duration = 0;
		Entity enemy = null;
		boolean shelled = false;
		if (!entity.isAlive()) {
			return;
		}
		spawn = entity instanceof OceanizedWitherEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWitherEntity.DATA_spawn) : 0;
		skillp = entity instanceof OceanizedWitherEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWitherEntity.DATA_skillp) : 0;
		duration = entity instanceof OceanizedWitherEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWitherEntity.DATA_duration) : 0;
		if (spawn < 100) {
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * (spawn + 1) * 0.01));
			if (entity instanceof OceanizedWitherEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWitherEntity.DATA_spawn, (int) (spawn + 1));
			if (spawn == 19) {
				if (entity instanceof OceanizedWitherEntity animatable)
					animatable.setTexture("oceanized_wither_inv");
			} else if (spawn == 39) {
				if (entity instanceof OceanizedWitherEntity animatable)
					animatable.setTexture("oceanized_wither");
			} else if (spawn == 49) {
				if (entity instanceof OceanizedWitherEntity animatable)
					animatable.setTexture("oceanized_wither_inv");
			} else if (spawn == 59) {
				if (entity instanceof OceanizedWitherEntity animatable)
					animatable.setTexture("oceanized_wither");
			} else if (spawn == 69) {
				if (entity instanceof OceanizedWitherEntity animatable)
					animatable.setTexture("oceanized_wither_inv");
			} else if (spawn == 79) {
				if (entity instanceof OceanizedWitherEntity animatable)
					animatable.setTexture("oceanized_wither");
			} else if (spawn == 89) {
				if (entity instanceof OceanizedWitherEntity animatable)
					animatable.setTexture("oceanized_wither_inv");
			} else if (spawn == 99) {
				if (entity instanceof OceanizedWitherEntity animatable)
					animatable.setTexture("oceanized_wither");
				if (world instanceof Level _level && !_level.isClientSide())
					_level.explode(null, x, y, z, 16, Level.ExplosionInteraction.MOB);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ocean_wither_spawn")), SoundSource.HOSTILE, 4, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ocean_wither_spawn")), SoundSource.HOSTILE, 4, 1, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.EXPLOSION, x, y, z, 4, 3, 3, 3, 1);
			}
		}
		if (duration > 0) {
			if (entity instanceof OceanizedWitherEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWitherEntity.DATA_duration, (int) (duration - 1));
		}
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (skillp > 0) {
			if (entity instanceof OceanizedWitherEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWitherEntity.DATA_skillp, (int) (skillp - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if (entity instanceof OceanizedWitherEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedWitherEntity.DATA_duration, 40);
				if (entity instanceof OceanizedWitherEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedWitherEntity.DATA_skillp, 300);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 20, 0, false, false));
				if (entity instanceof OceanizedWitherEntity) {
					((OceanizedWitherEntity) entity).setAnimation("animation.oceanzied_wither.skill");
				}
				CaerulaArborMod.queueServerWork(4, () -> {
					RimedWitherShootProcedure.execute(world, entity);
				});
				CaerulaArborMod.queueServerWork(13, () -> {
					GatlinWitherShootProcedure.execute(world, x, y, z, entity);
				});
				CaerulaArborMod.queueServerWork(12, () -> {
					GatlinWitherShootProcedure.execute(world, x, y, z, entity);
				});
			}
		}
		if (entity.tickCount % 10 == 0) {
			WitherDestroyBlockProcedure.execute(world, x, y, z);
		}
		if (entity.tickCount % 20 == 0) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.WITHER);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof LivingEntity _livEnt34 && _livEnt34.hasEffect(MobEffects.WITHER) && entityiterator.isAlive()) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_wither"))), entity),
								(float) ((entity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity36.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.75));
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, (entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ()), 16, 1, 1, 1, 0.1);
					}
				}
			}
		}
		if (entity.tickCount % 40 == 0) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(72 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof WitherSkull && GetSpeedProcedure.execute(entityiterator) < 0.15) {
						if (!entityiterator.level().isClientSide())
							entityiterator.discard();
					}
				}
			}
		}
		if (GetSpeedProcedure.execute(entity) > (entity instanceof LivingEntity _livingEntity48 && _livingEntity48.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity48.getAttribute(Attributes.MOVEMENT_SPEED).getValue() : 0)) {
			entity.setDeltaMovement(new Vec3(0, 0, 0));
		}
		shelled = entity instanceof OceanizedWitherEntity _datEntL50 && _datEntL50.getEntityData().get(OceanizedWitherEntity.DATA_shelled);
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5 && !shelled && spawn > 99) {
			if (entity instanceof LivingEntity _livingEntity54 && _livingEntity54.getAttributes().hasAttribute(Attributes.ARMOR))
				_livingEntity54.getAttribute(Attributes.ARMOR)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity53.getAttribute(Attributes.ARMOR).getBaseValue() : 0) * 1.5));
			if (entity instanceof LivingEntity _livingEntity56 && _livingEntity56.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
				_livingEntity56.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
						.setBaseValue(((entity instanceof LivingEntity _livingEntity55 && _livingEntity55.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
								? _livingEntity55.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
								: 0) * 1.5));
			if (entity instanceof LivingEntity _livingEntity58 && _livingEntity58.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity58.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity57 && _livingEntity57.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity57.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.5));
			if (entity instanceof OceanizedWitherEntity animatable)
				animatable.setTexture("oceanized_wither_anger");
			if (entity instanceof OceanizedWitherEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceanizedWitherEntity.DATA_shelled, true);
		}
	}
}
