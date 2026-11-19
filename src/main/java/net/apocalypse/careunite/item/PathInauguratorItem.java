
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.SimulatedHoeProcedure;

import java.util.List;
import net.minecraftforge.common.ToolAction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.ToolActions;

public class PathInauguratorItem extends AxeItem {
	public PathInauguratorItem() {
		super(new Tier() {
			public int getUses() {
				return 10025;
			}

			public float getSpeed() {
				return 11f;
			}

			public float getAttackDamageBonus() {
				return 12f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 14;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}
		}, 1, -2.5f, new Item.Properties().fireResistant());
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState blockstate) {
		return blockstate.is(BlockTags.MINEABLE_WITH_AXE) || blockstate.is(BlockTags.MINEABLE_WITH_HOE);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
		return ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction);
	}


	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.path_inaugurator.description_0"));
	}

//	@Override
//	public InteractionResult useOn(UseOnContext context) {
//		super.useOn(context);
//		return SimulatedHoeProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()), context.getClickedFace(),
//				context.getPlayer(), context.getItemInHand());
//	}
}
