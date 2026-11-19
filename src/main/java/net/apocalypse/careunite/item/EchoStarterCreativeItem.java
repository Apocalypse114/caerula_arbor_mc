
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;

import net.apocalypse.careunite.procedures.StartAllEchoProcedure;

public class EchoStarterCreativeItem extends Item {
	public EchoStarterCreativeItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		StartAllEchoProcedure.execute(entity);
		return ar;
	}
}
