
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

public class MuteAttackEnchantment extends Enchantment {
	private static final Ingredient TAG = Ingredient.of(ItemTags.TOOLS);
	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("caerula_arbor_reunite_mute_attack",
		item -> EnchantmentCategory.WEAPON.canEnchant(item) || EnchantmentCategory.DIGGER.canEnchant(item) 
			|| TAG.test(new ItemStack(item)));

	public MuteAttackEnchantment() {
		super(Enchantment.Rarity.RARE, ENCHANTMENT_CATEGORY, EquipmentSlot.values());
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
