package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class GetMortarBoxDescProcedure {
	public static String execute(ItemStack itemstack) {
		double ggg = 0;
		double ccc = 0;
		String gunpoeder = "";
		String core = "";
		String rev = "";
		ggg = itemstack.getOrCreateTag().getDouble("gunpowder");
		ccc = itemstack.getOrCreateTag().getDouble("core");
		rev = Component.translatable("item.caerula_arbor_reunite.aegir_mortar_gunpowder_box.desc0").getString() + "\n" + Component.translatable("item.caerula_arbor_reunite.aegir_mortar_gunpowder_box.desc1").getString();
		if (ggg > 0) {
			gunpoeder = new ItemStack(Items.GUNPOWDER).getDisplayName().getString() + "+" + (int) ggg;
			rev = rev + "\n" + gunpoeder;
		}
		if (ccc > 0) {
			core = new ItemStack(CaerulaArborModItems.TRAIL_POWDER_CORE.get()).getDisplayName().getString() + "+" + (int) ccc;
			rev = rev + "\n" + core;
		}
		return rev;
	}
}
