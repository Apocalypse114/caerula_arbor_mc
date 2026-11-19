
package net.apocalypse.careunite.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.procedures.PlaceSnifferEggOnBlockProcedure;

public class NetherseaSnifferEggItem extends Item {
	public NetherseaSnifferEggItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return PlaceSnifferEggOnBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getClickedFace(), context.getItemInHand());
	}
}
