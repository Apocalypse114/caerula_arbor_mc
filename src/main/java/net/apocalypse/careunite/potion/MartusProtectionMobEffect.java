
package net.apocalypse.careunite.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.MartusBuffParticleProcedure;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;
import java.util.ArrayList;

public class MartusProtectionMobEffect extends MobEffect {
	public MartusProtectionMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -16777063);
		this.addAttributeModifier(Attributes.LUCK, "a1fe5c70-5eb0-326e-ab95-ca86390674e0", 1, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(CaerulaArborModAttributes.GENERAL_DEFENSE.get(), "39a8533e-a27c-3ff8-b45b-150f72207a18", 9, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), "8b082d27-ef84-3136-8123-1bd5642da4c4", 75, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(CaerulaArborModAttributes.MISSRATE.get(), "7ec16e5d-eb5c-3590-b108-74acf31d12e0", 25, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> cures = new ArrayList<ItemStack>();
		return cures;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		MartusBuffParticleProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
