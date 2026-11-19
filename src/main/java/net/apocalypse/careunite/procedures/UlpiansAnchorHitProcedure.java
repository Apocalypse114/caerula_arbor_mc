package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;
import java.util.Comparator;

public class UlpiansAnchorHitProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double perc = 0;
		double damage = 0;
		double noeX = 0;
		double nowY = 0;
		double nowZ = 0;
		perc = (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.PATH_TO_UNCOVER.get(), 500, 0, false, true));
		if (entity instanceof LivingEntity _entity)
			_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * perc));
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		damage = (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1.5;
		if (!(enemy == null)) {
			{
				Entity _ent = entity;
				_ent.teleportTo((enemy.getX()), (enemy.getY()), (enemy.getZ()));
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport((enemy.getX()), (enemy.getY()), (enemy.getZ()), _ent.getYRot(), _ent.getXRot());
			}
			if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 120, 0, false, false));
			enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:anchor_smash"))), entity), (float) damage);
		}
		noeX = entity.getX();
		nowY = entity.getY();
		nowZ = entity.getZ();
		{
			final Vec3 _center = new Vec3(noeX, nowY, nowZ);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator instanceof Player || (entityiterator instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false)) {
					continue;
				}
				if (entityiterator == entity) {
					continue;
				}
				if (entityiterator == enemy) {
					continue;
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 6 && IsValidEnemyForIreneProcedure.execute(entityiterator, entity)) {
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 120, 0, false, false));
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:anchor_smash"))), entity),
							(float) damage);
				}
			}
		}
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.DIG_SLOWDOWN);
		if (entity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()))
			_livingEntity31.getAttribute(CaerulaArborModAttributes.SANITY.get()).setBaseValue(1000);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, noeX, nowY, nowZ, 72, 3, 3, 3, 0.5);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(noeX, nowY, nowZ), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:anchor_skill")), SoundSource.PLAYERS, 3, 1);
			} else {
				_level.playLocalSound(noeX, nowY, nowZ, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:anchor_skill")), SoundSource.PLAYERS, 3, 1, false);
			}
		}
	}
}
