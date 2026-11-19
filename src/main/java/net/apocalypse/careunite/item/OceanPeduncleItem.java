
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.PlantPedOnFarmlandProcedure;
import net.apocalypse.careunite.procedures.EatPeduncleProcedure;

import java.util.List;

public class OceanPeduncleItem extends Item {
	public OceanPeduncleItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(3).saturationMod(1f).meat().build()));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.ocean_peduncle.description_0"));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		EatPeduncleProcedure.execute(world, x, y, z, entity);
		return retval;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return PlantPedOnFarmlandProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()),
				context.getClickedFace(), context.getItemInHand());
	}
}
