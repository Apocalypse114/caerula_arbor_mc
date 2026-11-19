package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedHoglinEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class HoglinDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		CaerulaArborMod.queueServerWork(9, () -> {
			if (sourceentity.isAlive()) {
				if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3) {
					if (entity instanceof OceanizedHoglinEntity _datEntL2 && _datEntL2.getEntityData().get(OceanizedHoglinEntity.DATA_isNetherite)) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) ((sourceentity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.5
										* Mth.nextInt(RandomSource.create(), 2, 7)));
						if (Math.random() <= 0.65) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ATTACK_COUNTER_BACK.get(), 280, 0, false, false));
							entity.push(0, 0.33, 0);
						} else {
							entity.push(0, 0.4, 0);
						}
					} else {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) ((sourceentity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity10.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.5
										* Mth.nextInt(RandomSource.create(), 2, 6)));
						if (Math.random() <= 0.5) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ATTACK_COUNTER_BACK.get(), 240, 0, false, false));
							entity.push(0, 0.33, 0);
						} else {
							entity.push(0, 0.4, 0);
						}
					}
				}
			}
		});
	}
}
