
package net.apocalypse.careunite.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import java.util.List;
import java.util.ArrayList;

public class SuperDestructiveModeMobEffect extends MobEffect {
	public SuperDestructiveModeMobEffect() {
		super(MobEffectCategory.NEUTRAL, -1);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "8c39e05d-27d1-38b7-b231-122cb0fd0149", 3.3, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> cures = new ArrayList<ItemStack>();
		return cures;
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
