package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.entity.OceanizedBruteEntity;

public class BruteAdddamageProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double ablty = 0;
		ablty = sourceentity instanceof OceanizedBruteEntity _datEntI ? _datEntI.getEntityData().get(OceanizedBruteEntity.DATA_ability) : 0;
		if (ablty < 7) {
			if (sourceentity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue(((sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) + 4));
			if (sourceentity instanceof OceanizedBruteEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedBruteEntity.DATA_ability, (int) (ablty + 1));
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.LAVA, x, (y + 0.75), z, 32, 0.75, 0.75, 0.75, 0.1);
		}
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
			if (sourceentity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.1));
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, (y + 0.75), z, 32, 0.75, 0.75, 0.75, 0.1);
		}
	}
}
