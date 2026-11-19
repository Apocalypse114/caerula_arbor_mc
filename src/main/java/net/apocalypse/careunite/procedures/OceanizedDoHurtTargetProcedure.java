package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.OceanizedSpiderEntity;
import net.apocalypse.careunite.entity.OceanizedRavagerEntity;
import net.apocalypse.careunite.entity.OceanizedPolarBearEntity;
import net.apocalypse.careunite.entity.OceanizedCatEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class OceanizedDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof OceanizedCatEntity) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cat.hiss")), SoundSource.HOSTILE, (float) 0.75, (float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cat.hiss")), SoundSource.HOSTILE, (float) 0.75, (float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15), false);
				}
			}
			CaerulaArborMod.queueServerWork(9, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 2) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity5.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			CaerulaArborMod.queueServerWork(14, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity11.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			return;
		}
		if (sourceentity instanceof OceanizedRavagerEntity) {
			CaerulaArborMod.queueServerWork(11, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3.8) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity18.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			return;
		}
		if (sourceentity instanceof OceanizedPolarBearEntity) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.polar_bear.warning")), SoundSource.HOSTILE, 8, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.polar_bear.warning")), SoundSource.HOSTILE, 8, 1, false);
				}
			}
			CaerulaArborMod.queueServerWork(7, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity26.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			CaerulaArborMod.queueServerWork(12, () -> {
				if (sourceentity.isAlive()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.polar_bear.step")), SoundSource.HOSTILE, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.polar_bear.step")), SoundSource.HOSTILE, 1, 1, false);
						}
					}
					PolarRangedProcedure.execute(world, sourceentity);
				}
			});
			return;
		}
		if (sourceentity instanceof OceanizedSpiderEntity) {
			CaerulaArborMod.queueServerWork(10, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 2.6) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity36.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
		}
	}
}
