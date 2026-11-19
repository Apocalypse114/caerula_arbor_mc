
package net.apocalypse.careunite.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.RegeneratePerclyProcedure;

public class RegenerationPerclyMobEffect extends MobEffect {
	public RegenerationPerclyMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -32383);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		RegeneratePerclyProcedure.execute(entity, amplifier);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
