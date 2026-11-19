
package net.apocalypse.careunite.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class SanityCompensoryMobEffect extends MobEffect {
	public SanityCompensoryMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -16724788);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
