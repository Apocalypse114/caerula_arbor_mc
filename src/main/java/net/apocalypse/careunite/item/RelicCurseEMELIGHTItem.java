
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.CaerulaUtil;

import net.apocalypse.careunite.procedures.PlaceLamternProcedure;
import net.apocalypse.careunite.procedures.GainCursedEMELIGHTProcedure;
import net.apocalypse.careunite.procedures.CursedDescProcedure;

import java.util.List;

public class RelicCurseEMELIGHTItem extends Item {
	public RelicCurseEMELIGHTItem() {
		super(new Item.Properties().stacksTo(1).rarity(CaerulaUtil.RelicRarity.CURSED));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		Entity entity = itemstack.getEntityRepresentation();
		String hoverText = CursedDescProcedure.execute(itemstack);
		if (hoverText != null) {
			for (String line : hoverText.split("\n")) {
				list.add(Component.literal(line));
			}
		}
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return PlaceLamternProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getClickedFace(), context.getPlayer(), context.getItemInHand());
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		GainCursedEMELIGHTProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
	}
}
