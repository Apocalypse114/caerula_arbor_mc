
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

import net.apocalypse.careunite.procedures.RemoveRepellerProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import java.util.List;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class TideBishopSpawneggItem extends ForgeSpawnEggItem {
	public TideBishopSpawneggItem() {
		super(CaerulaArborModEntities.TIDE_BISHOP, -1, -1, new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.tide_bishop_spawnegg.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.tide_bishop_spawnegg.description_1"));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		RemoveRepellerProcedure.execute(entity);
		return true;
	}
}
