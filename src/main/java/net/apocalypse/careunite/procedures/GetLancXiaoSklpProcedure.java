package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public class GetLancXiaoSklpProcedure {
	public static String execute(ItemStack itemstack) {
		double sklp = 0;
		String prefix = "";
		sklp = 6 - itemstack.getOrCreateTag().getDouble("sklp");
		if (sklp >= 6) {
			prefix = Component.translatable("item.caerula_arbor_reunite.lanc_xiao.skill").getString() + "\u00A7b";
		} else {
			prefix = Component.translatable("item.caerula_arbor_reunite.lanc_xiao.skill").getString() + "\u00A7p";
		}
		return Component.translatable("item.caerula_arbor_reunite.lanc_xiao.description_0").getString() + "\n" + Component.translatable("item.caerula_arbor_reunite.lanc_xiao.description_1").getString() + "\n"
				+ Component.translatable("item.caerula_arbor_reunite.lanc_xiao.description_2").getString() + "\n" + prefix + Math.round(sklp) + " / 6";
	}
}
