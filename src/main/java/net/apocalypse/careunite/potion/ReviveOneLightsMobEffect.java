
package net.apocalypse.careunite.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.SlowlyReviveOneLightsProcedure;
import net.apocalypse.careunite.procedures.CheckDurationGapProcedure;

public class ReviveOneLightsMobEffect extends MobEffect {
	public ReviveOneLightsMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -6684673);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		SlowlyReviveOneLightsProcedure.execute(entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return CheckDurationGapProcedure.execute(amplifier, duration);
	}
}
