package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;

public class IsFilledwithPersonnelProcedure {
	public static boolean execute(ItemStack itemstack) {
		String name = "";
		name = itemstack.getOrCreateTag().getString("name");
		if (name == null) {
			return false;
		}
		if ((name).equals("apocata")) {
			return false;
		}
		return !(name).isEmpty();
	}
}
