package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.NetherseaSlimeEntity;

public class GetSlimeSizeProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		double size = 0;
		return (entity instanceof NetherseaSlimeEntity _datEntI ? _datEntI.getEntityData().get(NetherseaSlimeEntity.DATA_SIZE) : 0) * 0.5;
	}
}
