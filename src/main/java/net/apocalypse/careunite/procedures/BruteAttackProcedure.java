package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class BruteAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double sklp = 0;
		sklp = Math.max(
				Math.min((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 4,
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.25),
				(entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1);
		if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 4) {
			sourceentity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), entity),
					(float) sklp);
			if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 120, 0, false, false));
			if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ROCK_BREAK.get(), 120, 0, false, false));
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.break")), SoundSource.HOSTILE, 2, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.break")), SoundSource.HOSTILE, 2, 1, false);
			}
		}
		{
			final Vec3 _center = new Vec3((x + 2 * entity.getLookAngle().x), y, (z + 2 * entity.getLookAngle().z));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof Mob)) {
					continue;
				}
				if (entityiterator == sourceentity) {
					continue;
				}
				if (entityiterator == entity) {
					continue;
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 3 && IsValidEnemyForSeabornProcedure.execute(entityiterator, entity)) {
					sklp = Math.max(
							Math.min((entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 3,
									(entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.25),
							(entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity17.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1);
					entityiterator.hurt(
							new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), entity),
							(float) sklp);
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 120, 0, false, false));
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ROCK_BREAK.get(), 120, 0, false, false));
				}
			}
		}
	}
}
