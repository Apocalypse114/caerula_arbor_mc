package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.entity.NoviceAlchemistEntity;

public class ThunderHitNoviceProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0));
		if (entity instanceof NoviceAlchemistEntity _datEntSetL)
			_datEntSetL.getEntityData().set(NoviceAlchemistEntity.DATA_IS_BLUE, true);
	}
}
