
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.EatChocoIcecreamProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;

import java.util.List;

public class ChocolateIcecreamItem extends Item {
	public ChocolateIcecreamItem() {
		super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(6).saturationMod(1f).alwaysEat().build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 24;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.chocolate_icecream.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.chocolate_icecream.description_1"));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = new ItemStack(CaerulaArborModItems.SHELL_OF_STONECUTTER.get());
		super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		EatChocoIcecreamProcedure.execute(entity);
		if (itemstack.isEmpty()) {
			return retval;
		} else {
			if (entity instanceof Player player && !player.getAbilities().instabuild) {
				if (!player.getInventory().add(retval))
					player.drop(retval, false);
			}
			return itemstack;
		}
	}
}
