package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public class GetEchoGoldStarterDescProcedure {
	public static String execute(ItemStack itemstack) {
		if (itemstack.getOrCreateTag().getBoolean("used")) {
			return Component.translatable("item.caerula_arbor_reunite.echo_starter_gold.description_0").getString();
		}
		return Component.translatable("item.caerula_arbor_reunite.echo_starter.description_0").getString();
	}
}
