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

import net.apocalypse.careunite.entity.UlpiansEntity;
import net.apocalypse.careunite.entity.SpecterEntity;
import net.apocalypse.careunite.entity.SkadiEntity;
import net.apocalypse.careunite.entity.GladiiaEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class AbyssalHunterDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		if (sourceentity instanceof SkadiEntity) {
			CaerulaArborMod.queueServerWork(14, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3.5) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:skadi_attack")), SoundSource.HOSTILE, 5, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:skadi_attack")), SoundSource.HOSTILE, 5, 1, false);
							}
						}
						entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hunter_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
		} else if (sourceentity instanceof SpecterEntity) {
			if (sourceentity instanceof SpecterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SpecterEntity.DATA_duration, (int) ((sourceentity instanceof SpecterEntity _datEntI ? _datEntI.getEntityData().get(SpecterEntity.DATA_duration) : 0) + 30));
			if (sourceentity instanceof SpecterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SpecterEntity.DATA_skillp1, (int) ((sourceentity instanceof SpecterEntity _datEntI ? _datEntI.getEntityData().get(SpecterEntity.DATA_skillp1) : 0) + 1));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:specter_attack")), SoundSource.HOSTILE, (float) 2.5,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:specter_attack")), SoundSource.HOSTILE, (float) 2.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			CaerulaArborMod.queueServerWork(12, () -> {
				if (sourceentity.isAlive()) {
					SpecterSawAttackProcedure.execute(world, entity, sourceentity);
				}
			});
		} else if (sourceentity instanceof GladiiaEntity) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_attack_pre")), SoundSource.NEUTRAL, (float) 2.2,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_attack_pre")), SoundSource.NEUTRAL, (float) 2.2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			CaerulaArborMod.queueServerWork(9, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 5) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_attack_hit")), SoundSource.NEUTRAL, (float) 2.75, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_attack_hit")), SoundSource.NEUTRAL, (float) 2.75, 1, false);
							}
						}
						entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hunter_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity23 && _livingEntity23.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity23.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
		} else if (sourceentity instanceof UlpiansEntity) {
			if (sourceentity instanceof UlpiansEntity _datEntSetI)
				_datEntSetI.getEntityData().set(UlpiansEntity.DATA_duration, (int) ((sourceentity instanceof UlpiansEntity _datEntI ? _datEntI.getEntityData().get(UlpiansEntity.DATA_duration) : 0) + 30));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:anchor_pre")), SoundSource.HOSTILE, (float) 2.2, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:anchor_pre")), SoundSource.HOSTILE, (float) 2.2, 1, false);
				}
			}
			CaerulaArborMod.queueServerWork(14, () -> {
				if (sourceentity.isAlive()) {
					UlpiansRangedProcedure.execute(world, x, y, z, sourceentity, 1);
				}
			});
		}
	}
}
