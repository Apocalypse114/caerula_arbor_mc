package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedPiglinEntity;
import net.apocalypse.careunite.entity.OceanizedHoglinEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class BruteDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		if (sourceentity instanceof OceanizedPiglinEntity) {
			CaerulaArborMod.queueServerWork(6, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 2.5) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) ((sourceentity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.5));
						if ((sourceentity.getVehicle()) instanceof OceanizedHoglinEntity && Math.random() <= 0.35) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ATTACK_COUNTER_BACK.get(), 160, 0, false, false));
						}
					}
				}
			});
			CaerulaArborMod.queueServerWork(12, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity12.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
						if ((sourceentity.getVehicle()) instanceof OceanizedHoglinEntity && Math.random() <= 0.5) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ATTACK_COUNTER_BACK.get(), 160, 0, false, false));
						}
					}
				}
			});
		} else {
			CaerulaArborMod.queueServerWork(10, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 2.6) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity21.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
						ShieldBreakProcedure.execute(world, x, y, z, entity, 50);
					}
				}
			});
		}
	}
}
