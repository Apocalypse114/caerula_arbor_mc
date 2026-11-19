
package net.apocalypse.careunite.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.EracesArmorErrosionProcedure;

public class LessArmorImmueMobEffect extends MobEffect {
	public LessArmorImmueMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -6710785);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		EracesArmorErrosionProcedure.execute(entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
