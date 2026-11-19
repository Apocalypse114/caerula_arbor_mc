
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.SummonPuathshaperOnBlockProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import java.util.List;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class PathShaperSpawneggItem extends ForgeSpawnEggItem {
	public PathShaperSpawneggItem() {
		super(CaerulaArborModEntities.ROUTE_SHAPER, -1, -1, new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.path_shaper_spawnegg.description_0"));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		if(player != null && !player.isShiftKeyDown()) return super.useOn(context);
		return SummonPuathshaperOnBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getClickedFace(), context.getPlayer(), context.getItemInHand());
	}
}
