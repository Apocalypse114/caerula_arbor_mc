
package net.apocalypse.careunite.potion;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.OnTrappedTickProcedure;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;
import java.util.ArrayList;

public class TrappedMobEffect extends MobEffect {
	public TrappedMobEffect() {
		super(MobEffectCategory.HARMFUL, -26113);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "10f96171-1a53-3a1b-b8aa-3fb515934964", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
		this.addAttributeModifier(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), "b17e0e24-6b28-3617-a763-7b5e4a61dc7f", -15, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> cures = new ArrayList<ItemStack>();
		cures.add(new ItemStack(Items.TOTEM_OF_UNDYING));
		return cures;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		OnTrappedTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, amplifier);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
