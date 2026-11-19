
package net.apocalypse.careunite.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.ResetImmortalProcedure;
import net.apocalypse.careunite.procedures.PunishImmortaledEntityProcedure;
import net.apocalypse.careunite.procedures.ImmortalSummonPtcProcedure;
import net.apocalypse.careunite.procedures.FUncBy2TickProcedure;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;
import java.util.ArrayList;

public class ImmortalMobEffect extends MobEffect {
	public ImmortalMobEffect() {
		super(MobEffectCategory.NEUTRAL, -4648944);
		this.addAttributeModifier(CaerulaArborModAttributes.SANITY_RESISTANCE.get(), "bcdd0e0f-6ece-3c18-9266-a804849cd8fc", 100, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "7626f02f-92bf-3575-935f-e06e87d78bd1", 10, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> cures = new ArrayList<ItemStack>();
		return cures;
	}

	@Override
	public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
		super.addAttributeModifiers(entity, attributeMap, amplifier);
		ResetImmortalProcedure.execute(entity);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		ImmortalSummonPtcProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ());
	}

	@Override
	public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
		super.removeAttributeModifiers(entity, attributeMap, amplifier);
		PunishImmortaledEntityProcedure.execute(entity.level(), entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return FUncBy2TickProcedure.execute(duration);
	}
}
