
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.PutDownHugeBallProcedure;

import java.util.List;

public class ItemSliderBallHugeItem extends Item {
	public ItemSliderBallHugeItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.item_slider_ball_huge.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.item_slider_ball_huge.description_1"));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return PutDownHugeBallProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getClickedFace(), context.getItemInHand());
	}
}
