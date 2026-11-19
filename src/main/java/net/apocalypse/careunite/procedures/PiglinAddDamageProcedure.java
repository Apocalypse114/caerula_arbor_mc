package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.entity.OceanizedPiglinEntity;

public class PiglinAddDamageProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double ablty = 0;
		ablty = sourceentity instanceof OceanizedPiglinEntity _datEntI ? _datEntI.getEntityData().get(OceanizedPiglinEntity.DATA_ability) : 0;
		if (ablty < 5) {
			if (sourceentity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue(((sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) + 3));
			if (sourceentity instanceof OceanizedPiglinEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedPiglinEntity.DATA_ability, (int) (ablty + 1));
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.LAVA, x, (y + 0.75), z, 32, 0.75, 0.75, 0.75, 0.1);
		}
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
			if (sourceentity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.15));
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, (y + 0.75), z, 32, 0.75, 0.75, 0.75, 0.1);
		}
		if (entity instanceof Hoglin) {
			if (Math.random() < 0.1) {
				if (sourceentity instanceof OceanizedPiglinEntity) {
					((OceanizedPiglinEntity) sourceentity).setAnimation("animation.oceanized_piglin.celebrate");
				}
				if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 55, 9, false, false));
			}
		}
	}
}
