
package net.apocalypse.careunite.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SanityReaperEnchantment extends Enchantment {

	private static final Ingredient TAG = Ingredient.of(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:enchantable/sanity")));
	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("caerula_arbor_reunite_sanity_reaper",
		item -> EnchantmentCategory.WEAPON.canEnchant(item) || item instanceof AxeItem || TAG.test(new ItemStack(item)));

	public SanityReaperEnchantment() {
		super(Enchantment.Rarity.UNCOMMON, ENCHANTMENT_CATEGORY, EquipmentSlot.values());
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
		return 5;
	}
}
