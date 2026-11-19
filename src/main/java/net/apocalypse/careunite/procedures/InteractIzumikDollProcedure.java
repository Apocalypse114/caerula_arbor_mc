package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.entity.IzumilDollEntity;

public class InteractIzumikDollProcedure {
	public static InteractionResult execute(Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if ((entity instanceof IzumilDollEntity _datEntI ? _datEntI.getEntityData().get(IzumilDollEntity.DATA_TransformTick) : 0) < 0) {
			if (entity instanceof IzumilDollEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IzumilDollEntity.DATA_TransformTick, 0);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
