package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;

public class EchoDeviceDimProcedure {
	public static double execute(ItemStack itemstack) {
		if (itemstack.getOrCreateTag().getBoolean("used")) {
			return 1;
		}
		return 0;
	}
}
