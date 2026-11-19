
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.LiftOceanizationProcedure;

import java.util.List;

public class MutagenisisCapsuleItem extends Item {
	public MutagenisisCapsuleItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.RARE).food((new FoodProperties.Builder()).nutrition(2).saturationMod(1f).alwaysEat().build()));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.mutagenisis_capsule.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.mutagenisis_capsule.description_1"));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		LiftOceanizationProcedure.execute(world, entity);
		return retval;
	}
}
