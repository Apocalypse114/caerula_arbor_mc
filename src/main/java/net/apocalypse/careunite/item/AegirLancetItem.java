
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.GiveBloodOozeProcedure;
import net.apocalypse.careunite.procedures.Add80ReachProcedure;

import java.util.List;

public class AegirLancetItem extends SwordItem {
	public AegirLancetItem() {
		super(new Tier() {
			public int getUses() {
				return 1299;
			}

			public float getSpeed() {
				return 7f;
			}

			public float getAttackDamageBonus() {
				return 5f;
			}

			public int getLevel() {
				return 2;
			}

			public int getEnchantmentValue() {
				return 9;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}
		}, 3, -3f, new Item.Properties().fireResistant());
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		GiveBloodOozeProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceentity);
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
		list.add(Component.translatable("item.caerula_arbor_reunite.aegir_lancet.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.aegir_lancet.description_1"));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			Add80ReachProcedure.execute(entity);
	}
}
