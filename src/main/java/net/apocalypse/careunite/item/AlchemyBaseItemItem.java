
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.PutdownAlchemyBaseProcedure;
import net.apocalypse.careunite.procedures.OnEatAlchemyBaseProcedure;
import net.apocalypse.careunite.procedures.IsAlchemistSuitProcedure;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class AlchemyBaseItemItem extends Item {
	public AlchemyBaseItemItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0f).alwaysEat().build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 20;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.alchemy_base_item.description_0"));
		if (shouldEat()) list.add(Component.translatable("item.caerula_arbor_reunite.alchemy_base_item.description_1"));
		else {
			if (Screen.hasShiftDown()) list.add(Component.translatable("item.caerula_arbor_reunite.alchemy_base_item.note"));
			else list.add(Component.translatable("item.caerula_arbor_reunite.alchemy_base_item.description_2"));
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		if (IsAlchemistSuitProcedure.execute(entity)) return super.use(world, entity, hand);
		return InteractionResultHolder.pass(entity.getItemInHand(hand));
	}

	public static boolean shouldEat(){
		return IsAlchemistSuitProcedure.execute(Minecraft.getInstance().player);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		OnEatAlchemyBaseProcedure.execute(world, entity);
		return retval;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		PutdownAlchemyBaseProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getClickedFace(), context.getItemInHand());
		return InteractionResult.SUCCESS;
	}
}
