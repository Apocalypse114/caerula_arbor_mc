
package net.apocalypse.careunite.potion;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.GiveFireResisProcedure;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class InvulnerableMobEffect extends MobEffect {
	public InvulnerableMobEffect() {
		super(MobEffectCategory.NEUTRAL, -10092442);
		this.addAttributeModifier(CaerulaArborModAttributes.SANITY_RESISTANCE.get(), "3b009f60-e661-3e22-bb74-48e815ae0e8b", 100, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		GiveFireResisProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, amplifier);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
