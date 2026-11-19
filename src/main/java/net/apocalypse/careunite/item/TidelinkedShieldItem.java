
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.ClearPlayerLessArmorProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;

import java.util.List;

public class TidelinkedShieldItem extends ShieldItem {
	public TidelinkedShieldItem() {
		super(new Item.Properties().durability(3125).fireResistant());
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemstack, ItemStack repairitem) {
		return Ingredient.of(new ItemStack(CaerulaArborModItems.REPELLER_SHELL.get())).test(repairitem);
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
		list.add(Component.translatable("item.caerula_arbor_reunite.tidelinked_shield.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.tidelinked_shield.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.tidelinked_shield.description_2"));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			ClearPlayerLessArmorProcedure.execute(entity);
	}
}
