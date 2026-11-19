package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class CallInquisitionsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double num = 0;
		double tX = 0;
		double tZ = 0;
		double rand = 0;
		String log = "";
		if (!(entity instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(itemstack.getItem()))) {
			if (!entity.isShiftKeyDown()) {
				DispatchInquisitionProcedure.execute(world, entity, itemstack, entity.getLookAngle().x * 2 + x, y, entity.getLookAngle().z * 2 + z);
			} else {
				TeleportInquisitionsProcedure.execute(world, entity, itemstack, entity.getLookAngle().x * 2 + x, y, entity.getLookAngle().z * 2 + z);
			}
		}
	}
}
