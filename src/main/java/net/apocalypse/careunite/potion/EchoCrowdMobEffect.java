
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

public class EchoCrowdMobEffect extends MobEffect {
	public EchoCrowdMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -1);
		this.addAttributeModifier(Attributes.ARMOR, "839f433f-2a4d-3711-93a2-e5cc000b3012", 0.25, AttributeModifier.Operation.MULTIPLY_BASE);
		this.addAttributeModifier(CaerulaArborModAttributes.GENERAL_DEFENSE.get(), "3b7c70f1-8af5-3e54-9d56-fbcc150593ba", 0.25, AttributeModifier.Operation.MULTIPLY_BASE);
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
