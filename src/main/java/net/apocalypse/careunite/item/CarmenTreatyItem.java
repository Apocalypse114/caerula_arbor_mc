
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.SummonCarmenProcedure;

import java.util.List;

public class CarmenTreatyItem extends Item {
	public CarmenTreatyItem() {
		super(new Item.Properties().stacksTo(8).fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.carmen_treaty.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.carmen_treaty.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.carmen_treaty.description_2"));
		list.add(Component.translatable("item.caerula_arbor_reunite.carmen_treaty.description_3"));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return SummonCarmenProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()), context.getClickedFace(),
				context.getPlayer(), context.getItemInHand());
	}
}
