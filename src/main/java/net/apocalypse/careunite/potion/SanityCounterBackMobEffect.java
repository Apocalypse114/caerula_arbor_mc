
package net.apocalypse.careunite.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class SanityCounterBackMobEffect extends MobEffect {
	public SanityCounterBackMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -15735861);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
