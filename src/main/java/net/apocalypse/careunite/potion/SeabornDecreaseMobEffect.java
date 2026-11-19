
package net.apocalypse.careunite.potion;

import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.GuiGraphics;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;
import java.util.ArrayList;

public class SeabornDecreaseMobEffect extends MobEffect {
	public SeabornDecreaseMobEffect() {
		super(MobEffectCategory.HARMFUL, -1);
		this.addAttributeModifier(CaerulaArborModAttributes.GENERAL_DEFENSE.get(), "ecb18cfd-7eaa-3f0b-89b5-de5fa00a28e2", -0.5, AttributeModifier.Operation.MULTIPLY_BASE);
		this.addAttributeModifier(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), "3273d05f-4943-3cb1-b4e2-9f09876b53aa", -25, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(CaerulaArborModAttributes.SANITY_MODIFIER.get(), "32057722-a0b0-3680-a036-cebcb7617932", 0.125, AttributeModifier.Operation.MULTIPLY_TOTAL);
		this.addAttributeModifier(CaerulaArborModAttributes.MISSRATE.get(), "7f8d0b06-57d9-38be-90ac-0ef79ea04293", -15, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(Attributes.ARMOR, "7aec7a93-335a-394e-a99d-5b9ae1f007b5", -0.5, AttributeModifier.Operation.MULTIPLY_BASE);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "e649f79b-cf8c-3a59-b745-5aadf18ee92f", -0.33, AttributeModifier.Operation.MULTIPLY_BASE);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "dd94f1f3-9a47-3e1c-9f40-8b8d00122c78", -0.33, AttributeModifier.Operation.MULTIPLY_BASE);
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

	@Override
	public void initializeClient(java.util.function.Consumer<IClientMobEffectExtensions> consumer) {
		consumer.accept(new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInInventory(MobEffectInstance effect) {
				return false;
			}

			@Override
			public boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
				return false;
			}

			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		});
	}
}
