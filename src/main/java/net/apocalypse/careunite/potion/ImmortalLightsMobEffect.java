
package net.apocalypse.careunite.potion;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.ImmortalLightsFuncProcedure;
import net.apocalypse.careunite.procedures.FuncByHalfscdProcedure;

public class ImmortalLightsMobEffect extends MobEffect {
	public ImmortalLightsMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -13259064);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "10f048a4-c3ce-3483-9508-73e7c10be996", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		ImmortalLightsFuncProcedure.execute(entity, amplifier);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return FuncByHalfscdProcedure.execute(duration);
	}
}
