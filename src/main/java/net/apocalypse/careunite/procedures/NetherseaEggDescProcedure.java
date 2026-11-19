package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public class NetherseaEggDescProcedure {
	public static String execute(ItemStack itemstack) {
		double rrr = 0;
		double ooo = 0;
		String info = "";
		rrr = Math.max(itemstack.getOrCreateTag().getDouble("rate") * 0.1, 100);
		ooo = Math.max(itemstack.getOrCreateTag().getDouble("offset"), 4);
		return Component.translatable("item.caerula_arbor_reunite.nethersea_chicken_egg.rate").getString() + "" + new java.text.DecimalFormat("##.##").format(rrr) + "%" + "\n"
				+ Component.translatable("item.caerula_arbor_reunite.nethersea_chicken_egg.offset").getString() + new java.text.DecimalFormat("##.##").format(ooo);
	}
}
