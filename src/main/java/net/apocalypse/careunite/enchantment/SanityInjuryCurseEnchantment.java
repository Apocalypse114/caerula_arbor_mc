
package net.apocalypse.careunite.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;

import net.apocalypse.careunite.init.CaerulaArborModEnchantments;

import java.util.List;

public class SanityInjuryCurseEnchantment extends Enchantment {
//	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("caerula_arbor_reunite_sanity_injury_curse",
//			item -> Ingredient.of(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:enchantable/sanity_defend"))).test(new ItemStack(item)));

	public SanityInjuryCurseEnchantment() {
		super(Enchantment.Rarity.COMMON, SanityDefendEnchantment.ENCHANTMENT_CATEGORY, EquipmentSlot.values());
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
	protected boolean checkCompatibility(Enchantment enchantment) {
		return super.checkCompatibility(enchantment) && !List.of(CaerulaArborModEnchantments.SANITY_DEFEND.get()).contains(enchantment);
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
