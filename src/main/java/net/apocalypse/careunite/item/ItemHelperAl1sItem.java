
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.SummonAl1sOnBlockProcedure;

import java.util.List;

public class ItemHelperAl1sItem extends Item {
	public ItemHelperAl1sItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.item_helper_al_1s.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.item_helper_al_1s.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.item_helper_al_1s.description_2"));
		list.add(Component.translatable("item.caerula_arbor_reunite.item_helper_al_1s.description_3"));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return SummonAl1sOnBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getClickedFace(), context.getItemInHand());
	}
}
