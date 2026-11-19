package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;

public class LancXiaoReviveSklpProcedure {
	public static void execute(ItemStack itemstack) {
		itemstack.getOrCreateTag().putDouble("sklp", Math.max(itemstack.getOrCreateTag().getDouble("sklp") - 1, 0));
	}
}
