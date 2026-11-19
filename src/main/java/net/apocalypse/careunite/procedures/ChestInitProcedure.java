package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.entity.ChestFishEntity;

public class ChestInitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof ChestFishEntity _datEntL0 && _datEntL0.getEntityData().get(ChestFishEntity.DATA_release))) {
			entity.setShiftKeyDown(true);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 8, false, false));
		} else {
			entity.setShiftKeyDown(false);
		}
	}
}
