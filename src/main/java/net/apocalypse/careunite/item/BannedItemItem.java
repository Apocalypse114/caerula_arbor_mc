
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.GiveSeabornForgiveProcedure;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;

public class BannedItemItem extends Item {
	public BannedItemItem() {
		super(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.banned_item.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.banned_item.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.banned_item.description_2"));
	}

	@Override
    public @NotNull InteractionResult interactLivingEntity(
            @NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pTarget, @NotNull InteractionHand pHand
    ) {
		return GiveSeabornForgiveProcedure.execute(pTarget);
	}
}
