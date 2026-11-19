
package net.apocalypse.careunite.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.ReviveSanityProcedure;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;
import java.util.ArrayList;

public class SanityImmueMobEffect extends MobEffect {
	public SanityImmueMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -3342337);
		this.addAttributeModifier(CaerulaArborModAttributes.SANITY_RESISTANCE.get(), "25ed2265-a53a-32f6-8c94-a83714a7ad23", 200, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> cures = new ArrayList<ItemStack>();
		return cures;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		ReviveSanityProcedure.execute(entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
