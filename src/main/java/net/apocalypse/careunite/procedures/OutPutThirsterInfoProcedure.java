package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.entity.ThirsterEntity;

public class OutPutThirsterInfoProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("Dura" + (entity instanceof ThirsterEntity _datEntI ? _datEntI.getEntityData().get(ThirsterEntity.DATA_DURATION) : 0))), false);
		if (!world.isClientSide() && world.getServer() != null)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("Inte" + (entity instanceof ThirsterEntity _datEntI ? _datEntI.getEntityData().get(ThirsterEntity.DATA_INTEGRATION) : 0))), false);
	}
}
