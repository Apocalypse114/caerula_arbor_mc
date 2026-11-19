
package net.apocalypse.careunite.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;

public class RejectionCurseEnchantment extends Enchantment {
//	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("caerula_arbor_reunite_rejection_curse",
//			item -> Ingredient.of(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:enchantable/sanity_defend"))).test(new ItemStack(item)));

	public RejectionCurseEnchantment() {
		super(Enchantment.Rarity.VERY_RARE, SanityDefendEnchantment.ENCHANTMENT_CATEGORY, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
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
	public boolean isCurse() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return false;
	}
}
