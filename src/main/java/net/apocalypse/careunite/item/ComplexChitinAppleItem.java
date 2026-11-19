
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.EatComplChitinAppleProcedure;

import java.util.List;

public class ComplexChitinAppleItem extends Item {
	public ComplexChitinAppleItem() {
		super(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE).food((new FoodProperties.Builder()).nutrition(7).saturationMod(0.75f).alwaysEat().build()));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.complex_chitin_apple.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.complex_chitin_apple.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.complex_chitin_apple.description_2"));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		EatComplChitinAppleProcedure.execute(entity);
		return retval;
	}
}
