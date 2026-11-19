package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.particles.ParticleTypes;

public class CarmenBulletPtcProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		world.addParticle(ParticleTypes.END_ROD, x, y, z, ((-0.05) * immediatesourceentity.getDeltaMovement().x()), ((-0.05) * immediatesourceentity.getDeltaMovement().y()), ((-0.05) * immediatesourceentity.getDeltaMovement().z()));
		if (immediatesourceentity.tickCount >= 200) {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		}
	}
}
