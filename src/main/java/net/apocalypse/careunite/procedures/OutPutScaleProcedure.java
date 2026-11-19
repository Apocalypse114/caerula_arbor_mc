package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class OutPutScaleProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("scale of " + entity.getDisplayName().getString() + "(W x H):" + entity.getBbWidth() + "x" + entity.getBbHeight())), false);
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("look of" + entity.getDisplayName().getString() + "x " + Math.round(Math.pow(10, 3) * (entity.getLookAngle().x)) / Math.pow(10, 3) + " y "
					+ Math.round(Math.pow(10, 3) * (entity.getLookAngle().y)) / Math.pow(10, 3) + " z " + Math.round(Math.pow(10, 3) * (entity.getLookAngle().z)) / Math.pow(10, 3))), false);
	}
}
