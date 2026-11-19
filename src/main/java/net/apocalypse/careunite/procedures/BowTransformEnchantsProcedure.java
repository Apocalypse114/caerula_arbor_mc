package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;

import net.apocalypse.careunite.init.CaerulaArborModEnchantments;

import java.util.Map;

public class BowTransformEnchantsProcedure {
	public static void execute(LevelAccessor world, ItemStack itemstack) {
		double beforeLevel = 0;
		double afterLevel = 0;
		if (itemstack.getOrCreateTag().getBoolean("processed")) {
			return;
		}
		beforeLevel = itemstack.getEnchantmentLevel(CaerulaArborModEnchantments.REFLECTION.get());
		afterLevel = itemstack.getEnchantmentLevel(Enchantments.POWER_ARROWS);
		{
			Map<Enchantment, Integer> _enchantments = EnchantmentHelper.getEnchantments(itemstack);
			if (_enchantments.containsKey(CaerulaArborModEnchantments.REFLECTION.get())) {
				_enchantments.remove(CaerulaArborModEnchantments.REFLECTION.get());
				EnchantmentHelper.setEnchantments(_enchantments, itemstack);
			}
		}
		if (beforeLevel > afterLevel) {
			itemstack.enchant(Enchantments.POWER_ARROWS, (int) beforeLevel);
		}
		beforeLevel = itemstack.getEnchantmentLevel(CaerulaArborModEnchantments.METABOLISM.get());
		afterLevel = itemstack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS);
		{
			Map<Enchantment, Integer> _enchantments = EnchantmentHelper.getEnchantments(itemstack);
			if (_enchantments.containsKey(CaerulaArborModEnchantments.METABOLISM.get())) {
				_enchantments.remove(CaerulaArborModEnchantments.METABOLISM.get());
				EnchantmentHelper.setEnchantments(_enchantments, itemstack);
			}
		}
		if (beforeLevel > afterLevel) {
			itemstack.enchant(Enchantments.INFINITY_ARROWS, (int) beforeLevel);
		}
		itemstack.getOrCreateTag().putBoolean("processed", true);
	}
}
