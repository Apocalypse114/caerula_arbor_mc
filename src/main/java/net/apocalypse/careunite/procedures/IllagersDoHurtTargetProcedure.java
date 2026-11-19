package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.OceanizedVindicatorEntity;
import net.apocalypse.careunite.entity.OceanizedVillagerEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class IllagersDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		if (sourceentity instanceof OceanizedVindicatorEntity) {
			CaerulaArborMod.queueServerWork(12, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3.5) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
						if (Math.random() < 0.5) {
							ShieldBreakProcedure.execute(world, x, y, z, entity, 50);
						}
					}
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 1.5) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) ((sourceentity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity7.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.5));
					}
				}
			});
			CaerulaArborMod.queueServerWork(13, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 1.8) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) ((sourceentity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity13.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.5));
					}
				}
			});
		} else if (sourceentity instanceof OceanizedVillagerEntity) {
			CaerulaArborMod.queueServerWork(17, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 2) {
						entity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity20.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
		}
	}
}
