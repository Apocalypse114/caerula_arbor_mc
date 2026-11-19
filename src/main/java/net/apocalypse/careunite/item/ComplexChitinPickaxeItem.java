
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.CauseLessSanityProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;

import net.apocalypse.careunite.CaerulaTiers;

import java.util.List;
import net.minecraft.world.item.Rarity;

public class ComplexChitinPickaxeItem extends PickaxeItem {
	public ComplexChitinPickaxeItem() {
		super(CaerulaTiers.COMPLEX_CHITIN, 1, -2.8f, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		CauseLessSanityProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return retval;
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
		ItemStack retval = new ItemStack(this);
		retval.setDamageValue(itemstack.getDamageValue() + 1);
		if (retval.getDamageValue() >= retval.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return retval;
	}

	@Override
	public boolean isRepairable(ItemStack itemstack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.complex_chitin_pickaxe.description_0"));
	}
}
