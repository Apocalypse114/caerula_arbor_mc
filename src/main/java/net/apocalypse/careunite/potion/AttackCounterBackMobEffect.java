
package net.apocalypse.careunite.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class AttackCounterBackMobEffect extends MobEffect {
	public AttackCounterBackMobEffect() {
		super(MobEffectCategory.HARMFUL, -6750208);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
