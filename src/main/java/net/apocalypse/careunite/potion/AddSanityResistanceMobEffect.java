
package net.apocalypse.careunite.potion;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class AddSanityResistanceMobEffect extends MobEffect {
	public AddSanityResistanceMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -13369396);
		this.addAttributeModifier(CaerulaArborModAttributes.SANITY_RESISTANCE.get(), "99269bc8-85b8-3f2f-af8d-3960cb769cae", 10, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
