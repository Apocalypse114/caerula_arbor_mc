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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class LongTimeKillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		double perc = 0;
		if (immediatesourceentity.tickCount >= 160) {
			perc = (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.PATH_TO_UNCOVER.get(), 500, 0, false, false));
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * perc));
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 72, 3, 3, 3, 0.5);
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator == entity) {
						continue;
					}
					if ((entityiterator instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == entity) {
						continue;
					}
					if (!(entityiterator instanceof Mob)) {
						continue;
					}
					if (entityiterator instanceof Player) {
						continue;
					}
					if (new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ())).distanceTo(new Vec3(x, y, z)) <= 6) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:anchor_smash"))), entity),
								(float) ((entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity16.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1.5));
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 120, 0, false, false));
					}
				}
			}
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.DIG_SLOWDOWN);
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:anchor_skill")), SoundSource.PLAYERS, (float) 2.5, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:anchor_skill")), SoundSource.PLAYERS, (float) 2.5, 1, false);
				}
			}
		}
	}
}
