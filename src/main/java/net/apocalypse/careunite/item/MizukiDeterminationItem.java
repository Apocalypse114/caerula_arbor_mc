
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.RightClickDeterminationProcedure;

import java.util.List;

public class MizukiDeterminationItem extends Item {
	public MizukiDeterminationItem() {
		super(new Item.Properties().stacksTo(16).fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.mizuki_determination.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.mizuki_determination.description_1"));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return RightClickDeterminationProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()),
				context.getPlayer());
	}
}
