
package net.apocalypse.careunite.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;

import net.apocalypse.careunite.init.CaerulaArborModEnchantments;

import java.util.List;

public class MagicToleranceEnchantment extends Enchantment {
//	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("caerula_arbor_reunite_magic_tolerance",
//			item -> Ingredient.of(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:enchantable/sanity_defend"))).test(new ItemStack(item)));

	public MagicToleranceEnchantment() {
		super(Enchantment.Rarity.COMMON, SanityDefendEnchantment.ENCHANTMENT_CATEGORY, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
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

	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		return super.checkCompatibility(enchantment) && !List.of(CaerulaArborModEnchantments.FLEXIBILITY.get()).contains(enchantment);
	}
}
