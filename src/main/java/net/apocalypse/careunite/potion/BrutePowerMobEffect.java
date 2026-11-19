
package net.apocalypse.careunite.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import java.util.List;
import java.util.ArrayList;

public class BrutePowerMobEffect extends MobEffect {
	public BrutePowerMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -2249069);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "65001b98-89b7-3c1c-bdb5-ecde0ab59689", 0.15, AttributeModifier.Operation.MULTIPLY_BASE);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, "c417d2c2-7631-343c-9b2d-520acc42de26", -0.15, AttributeModifier.Operation.ADDITION);
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
