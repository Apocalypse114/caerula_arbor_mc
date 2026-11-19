package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public class OneUseItemDescProcedure {
	public static String execute(ItemStack itemstack) {
		String first_two = "";
		String locId = "";
		locId = itemstack.getDescriptionId();
		first_two = Component.translatable((locId + ".description_0")).getString() + "\n" + Component.translatable((locId + ".description_1")).getString();
		if (itemstack.getOrCreateTag().getBoolean("used")) {
			return first_two + "\n" + Component.translatable("item.caerula_arbor_reunite.relics.used").getString();
		}
		return first_two;
	}
}
