
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

import net.apocalypse.careunite.procedures.PlantPotatoOnFarmlandProcedure;
import net.apocalypse.careunite.procedures.EatNEtherseaPotatoProcedure;

import java.util.List;

public class NetherseaPotatoItem extends Item {
	public NetherseaPotatoItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.5f).build()));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.nethersea_potato.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.nethersea_potato.description_1"));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		EatNEtherseaPotatoProcedure.execute(entity);
		return retval;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return PlantPotatoOnFarmlandProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()),
				context.getClickedFace(), context.getItemInHand());
	}
}
