package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;

public class IsLancXiaoReadyProcedure {
	public static boolean execute(ItemStack itemstack) {
		return itemstack.getOrCreateTag().getDouble("sklp") <= 0;
	}
}
