
package net.apocalypse.careunite.item;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import java.util.List;

public class EnchantedEmeraldTreatyItem extends Item {
	public EnchantedEmeraldTreatyItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.enchanted_emerald_treaty.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.enchanted_emerald_treaty.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.enchanted_emerald_treaty.description_2"));
	}
}
