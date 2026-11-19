
package net.apocalypse.careunite.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SanityDefendEnchantment extends Enchantment {
	private static final Ingredient TAG = Ingredient.of(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:enchantable/sanity_defend")));
	public static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("caerula_arbor_reunite_sanity_defend",
			item -> EnchantmentCategory.ARMOR.canEnchant(item) || TAG.test(new ItemStack(item)));

	public SanityDefendEnchantment() {
		super(Enchantment.Rarity.COMMON, ENCHANTMENT_CATEGORY, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}

	@Override
	public int getMinCost(int level) {
		return 1 + level * 10;
	}

	@Override
	public int getMaxCost(int level) {
		return 6 + level * 10;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}
}
