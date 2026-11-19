
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

import net.apocalypse.careunite.init.CaerulaArborModEntities;

import net.apocalypse.careunite.procedures.SummonWardenOnBlockProcedure;

import java.util.List;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class OceanizedWardenSpawneggItem extends ForgeSpawnEggItem {
	public OceanizedWardenSpawneggItem() {
		super(CaerulaArborModEntities.OCEANIZED_WARDEN, -1, -1, new Item.Properties().stacksTo(64).rarity(Rarity.EPIC));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.oceanized_warden_spawnegg.description_0"));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		if(player != null && !player.isShiftKeyDown()) return super.useOn(context);
		return SummonWardenOnBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getClickedFace(), context.getPlayer(), context.getItemInHand());
	}
}
