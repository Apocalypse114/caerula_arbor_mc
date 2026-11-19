
package net.apocalypse.careunite.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.procedures.EmeralCallTraderProcedure;

public class NetherseaEmeraldItem extends Item {
	public NetherseaEmeraldItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return EmeralCallTraderProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()), context.getPlayer(), context.getItemInHand());
	}
}
