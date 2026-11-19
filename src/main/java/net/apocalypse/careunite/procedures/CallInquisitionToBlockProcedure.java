package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;

public class CallInquisitionToBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity, ItemStack itemstack) {
		if (direction == null || entity == null)
			return;
		String log = "";
		double num = 0;
		double tX = 0;
		double tZ = 0;
		double rand = 0;
		double tY = 0;
		if (!(entity instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(itemstack.getItem()))) {
			if (!entity.isShiftKeyDown()) {
				DispatchInquisitionProcedure.execute(world, entity, itemstack, x + direction.getStepX() + 0.5, y + direction.getStepY(), z + direction.getStepZ() + 0.5);
			} else {
				TeleportInquisitionsProcedure.execute(world, entity, itemstack, x + direction.getStepX() + 0.5, y + direction.getStepY(), z + direction.getStepZ() + 0.5);
			}
		}
	}
}
