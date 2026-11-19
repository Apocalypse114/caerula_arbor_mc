package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.WarriorPriestEntity;
import net.apocalypse.careunite.entity.JuniorWarriorPriestEntity;
import net.apocalypse.careunite.entity.IreneEntity;
import net.apocalypse.careunite.entity.CorrectionalPhalanxyInfantryEntity;
import net.apocalypse.careunite.entity.CorrectinalPhalaxVanguardEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class InquisitionDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		if (sourceentity instanceof CorrectinalPhalaxVanguardEntity) {
			CaerulaArborMod.queueServerWork(9, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3.5) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			CaerulaArborMod.queueServerWork(14, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 4.5) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), sourceentity),
								(float) ((sourceentity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1.25));
					}
				}
			});
			return;
		}
		if (sourceentity instanceof CorrectionalPhalanxyInfantryEntity) {
			CaerulaArborMod.queueServerWork(10, () -> {
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), sourceentity),
						(float) (sourceentity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity14.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
			});
			return;
		}
		if (sourceentity instanceof WarriorPriestEntity) {
			CaerulaArborMod.queueServerWork(8, () -> {
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), sourceentity),
						(float) (sourceentity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity19.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1, false, true));
			});
			return;
		}
		if (sourceentity instanceof JuniorWarriorPriestEntity) {
			CaerulaArborMod.queueServerWork(11, () -> {
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), sourceentity),
						(float) (sourceentity instanceof LivingEntity _livingEntity25 && _livingEntity25.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity25.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
			});
			return;
		}
		if (sourceentity instanceof IreneEntity) {
			if (sourceentity instanceof IreneEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IreneEntity.DATA_skillp1, (int) ((sourceentity instanceof IreneEntity _datEntI ? _datEntI.getEntityData().get(IreneEntity.DATA_skillp1) : 0) + 1));
			if (sourceentity instanceof IreneEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IreneEntity.DATA_skillp2, (int) ((sourceentity instanceof IreneEntity _datEntI ? _datEntI.getEntityData().get(IreneEntity.DATA_skillp2) : 0) + 1));
			CaerulaArborMod.queueServerWork(6, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_attack")), SoundSource.NEUTRAL, (float) 2.5,
										(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_attack")), SoundSource.NEUTRAL, (float) 2.5,
										(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
							}
						}
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MUTE.get(), 60, 0, false, false));
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity42 && _livingEntity42.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity42.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			CaerulaArborMod.queueServerWork(11, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_attack")), SoundSource.NEUTRAL,
										(float) 2.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
							} else {
								_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_attack")), SoundSource.NEUTRAL, (float) 2.5,
										(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
							}
						}
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity53.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			return;
		}
	}
}
