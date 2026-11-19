
package net.apocalypse.careunite.potion;

import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;
import java.util.ArrayList;

public class ParalyzedMobEffect extends MobEffect {
	public ParalyzedMobEffect() {
		super(MobEffectCategory.HARMFUL, -15921097);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "97455a27-26d5-34bb-9e4e-9eac0cd0a5b9", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
		this.addAttributeModifier(Attributes.FOLLOW_RANGE, "b4b2bef7-813a-383f-a8ef-c6f123c14a9f", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
		this.addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "2c4fce10-c4c0-3095-9d85-b4690a9790e0", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "d764af16-576f-3586-8ba8-7d410c8b132a", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "82e58553-f7c9-3bbd-b47d-2a16b5ea92fd", 10, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(Attributes.FLYING_SPEED, "5164a5d5-c299-38ee-94c6-1b4e54500018", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
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
