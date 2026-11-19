
package net.apocalypse.careunite.potion;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.ElectConductProcedure;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class ElectricConductionMobEffect extends MobEffect {
	public ElectricConductionMobEffect() {
		super(MobEffectCategory.HARMFUL, -13369396);
		this.addAttributeModifier(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), "b84682b2-a6cc-3e4e-afd3-f1d42e39e82d", -0.1, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		ElectConductProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, amplifier);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
