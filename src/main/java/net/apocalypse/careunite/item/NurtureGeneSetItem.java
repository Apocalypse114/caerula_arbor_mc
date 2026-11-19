
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.EnablePlayerEvoProcedure;

import java.util.List;

public class NurtureGeneSetItem extends Item {
	public NurtureGeneSetItem() {
		super(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.nurture_gene_set.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.nurture_gene_set.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.nurture_gene_set.description_2"));
		list.add(Component.translatable("item.caerula_arbor_reunite.nurture_gene_set.description_3"));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		EnablePlayerEvoProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, ar.getObject());
		return ar;
	}
}
