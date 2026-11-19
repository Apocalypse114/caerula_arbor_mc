package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.CaerulaArborMod;

public class SpecterSawAttackProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 3.5) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:saw_cut"))), sourceentity),
					(float) ((sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.45));
		}
		CaerulaArborMod.queueServerWork(3, () -> {
			if (sourceentity.isAlive()) {
				if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 3.5) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:saw_cut"))), sourceentity),
							(float) ((sourceentity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.5));
				}
			}
		});
		CaerulaArborMod.queueServerWork(6, () -> {
			if (sourceentity.isAlive()) {
				if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 3.5) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:saw_cut"))), sourceentity),
							(float) ((sourceentity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity12.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.45));
				}
			}
		});
	}
}
