package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
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
import net.apocalypse.careunite.entity.GuideAbyssalEntity;
import net.apocalypse.careunite.entity.CrackerAbyssalEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class NetherseaDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		if (sourceentity instanceof GuideAbyssalEntity) {
			CaerulaArborMod.queueServerWork(10, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 2.8) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			return;
		}
		if (sourceentity instanceof CrackerAbyssalEntity) {
			CaerulaArborMod.queueServerWork(11, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity10.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:reefbreaker_attack")), SoundSource.HOSTILE, 10,
							(float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:reefbreaker_attack")), SoundSource.HOSTILE, 10, (float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15), false);
				}
			}
			amplifi = sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.REEF_CRACKER.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.REEF_CRACKER.get()).getAmplifier() : 0;
			if (sourceentity instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(CaerulaArborModMobEffects.REEF_CRACKER.get())) {
				if (amplifi < 14) {
					if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 120, (int) (amplifi + 1), false, false));
				} else {
					if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 120, 14, false, false));
				}
			} else {
				if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 120, 0, false, false));
			}
		}
	}
}
