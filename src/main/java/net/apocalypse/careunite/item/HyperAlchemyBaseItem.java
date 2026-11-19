
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.OnEatAlchemyHyperProcedure;
import net.apocalypse.careunite.procedures.IsAlchemistSuitProcedure;

import java.util.List;
import net.minecraft.client.gui.screens.Screen;

public class HyperAlchemyBaseItem extends Item {
	public HyperAlchemyBaseItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(3).saturationMod(0f).alwaysEat().build()));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.NONE;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 20;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		if (IsAlchemistSuitProcedure.execute(entity)) return super.use(world, entity, hand);
		return InteractionResultHolder.pass(entity.getItemInHand(hand));
	}


	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.hyper_alchemy_base.description_0"));
		if (AlchemyBaseItemItem.shouldEat()) list.add(Component.translatable("item.caerula_arbor_reunite.hyper_alchemy_base.description_1"));
		else {
			if (Screen.hasShiftDown()) list.add(Component.translatable("item.caerula_arbor_reunite.alchemy_base_item.note"));
			else list.add(Component.translatable("item.caerula_arbor_reunite.alchemy_base_item.description_2"));
		}

	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		OnEatAlchemyHyperProcedure.execute(world, entity);
		return retval;
	}
}
