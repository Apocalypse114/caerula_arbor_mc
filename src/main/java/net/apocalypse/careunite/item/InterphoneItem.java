
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.CommandInquisitionsProcedure;
import net.apocalypse.careunite.procedures.CallInquisitionsProcedure;
import net.apocalypse.careunite.procedures.CallInquisitionToBlockProcedure;

import java.util.List;

public class InterphoneItem extends Item {
	public InterphoneItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.interphone.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.interphone.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.interphone.description_2"));
		list.add(Component.translatable("item.caerula_arbor_reunite.interphone.description_3"));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		CallInquisitionsProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, ar.getObject());
		return ar;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		CallInquisitionToBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getClickedFace(), context.getPlayer(), context.getItemInHand());
		return InteractionResult.SUCCESS;
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		CommandInquisitionsProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceentity);
		return retval;
	}
}
