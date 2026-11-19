
package net.apocalypse.careunite.item;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;

import net.apocalypse.careunite.procedures.EatEnchAppleProcedure;

public class EnchantedTrailGoldenAppleItem extends Item {
	public EnchantedTrailGoldenAppleItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.EPIC).food((new FoodProperties.Builder()).nutrition(6).saturationMod(1f).alwaysEat().build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 40;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return true;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		EatEnchAppleProcedure.execute(world, x, y, z, entity);
		return retval;
	}
}
