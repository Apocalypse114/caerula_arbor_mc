
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.EatMizukiBiscuitProcedure;

import java.util.List;

public class MizukiBiscuitItem extends Item {
	public MizukiBiscuitItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.3f).alwaysEat().build()));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.mizuki_biscuit.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.mizuki_biscuit.description_1"));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		EatMizukiBiscuitProcedure.execute(entity);
		return retval;
	}
}
