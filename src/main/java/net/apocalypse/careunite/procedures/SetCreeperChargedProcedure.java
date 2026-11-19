package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.entity.CreeperPetEntity;

public class SetCreeperChargedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof CreeperPetEntity _datEntSetL)
			_datEntSetL.getEntityData().set(CreeperPetEntity.DATA_CHARGED, true);
		entity.clearFire();
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 0));
	}
}
