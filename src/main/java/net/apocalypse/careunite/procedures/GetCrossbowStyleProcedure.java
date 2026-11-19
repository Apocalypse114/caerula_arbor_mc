package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;

public class GetCrossbowStyleProcedure {
	public static double execute(ItemStack itemstack) {
		return (double) itemstack.getOrCreateTag().getInt("style");
	}
}
