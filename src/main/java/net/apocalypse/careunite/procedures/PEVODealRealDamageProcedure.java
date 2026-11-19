package net.apocalypse.careunite.procedures;

import org.checkerframework.checker.units.qual.h;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

public class PEVODealRealDamageProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount, double rate) {
		if (entity == null || sourceentity == null)
			return;
		double d = 0;
		double h = 0;
		if (sourceentity.isShiftKeyDown()) {
			return;
		}
		h = (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) - amount;
		d = Math.min((sourceentity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate, h - 1);
		if (d > 0) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:oceankiller_damage")))), (float) d);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.GLOW_SQUID_INK, x, (y + 0.75), z, 3, 0.75, 0.75, 0.75, 0.1);
		}
	}
}
