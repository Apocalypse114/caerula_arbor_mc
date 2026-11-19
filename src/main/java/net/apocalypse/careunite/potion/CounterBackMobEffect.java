
package net.apocalypse.careunite.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class CounterBackMobEffect extends MobEffect {
	public CounterBackMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -16764007);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
