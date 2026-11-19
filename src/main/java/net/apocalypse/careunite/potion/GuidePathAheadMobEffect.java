
package net.apocalypse.careunite.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.GuideLayTrailProcedure;
import net.apocalypse.careunite.procedures.FuncByHalfscdProcedure;

import java.util.List;
import java.util.ArrayList;

public class GuidePathAheadMobEffect extends MobEffect {
	public GuidePathAheadMobEffect() {
		super(MobEffectCategory.NEUTRAL, -13395457);
		this.addAttributeModifier(Attributes.ARMOR, "6d4b81d9-bed2-396c-918f-891f0ef22012", 12, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "97fa89c4-3837-3dcb-a05f-db0233b6a457", 9, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> cures = new ArrayList<ItemStack>();
		return cures;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		GuideLayTrailProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return FuncByHalfscdProcedure.execute(duration);
	}
}
