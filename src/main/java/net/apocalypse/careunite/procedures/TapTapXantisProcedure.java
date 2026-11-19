package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.XantisEntity;

public class TapTapXantisProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		double tapTick = 0;
		boolean isNiubi = false;
		tapTick = entity instanceof XantisEntity _datEntI ? _datEntI.getEntityData().get(XantisEntity.DATA_TAP_TICK) : 0;
		if ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.BANNED_ITEM.get()) : false) {
			isNiubi = entity instanceof XantisEntity _datEntL2 && _datEntL2.getEntityData().get(XantisEntity.DATA_NIUBI);
			if (isNiubi) {
				if (entity instanceof XantisEntity _datEntSetL)
					_datEntSetL.getEntityData().set(XantisEntity.DATA_NIUBI, false);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.LARGE_SMOKE, x, (y + 0.5), z, 32, 0.5, 0.5, 0.5, 0.1);
				return InteractionResult.SUCCESS;
			}
		}
		if (tapTick <= 0) {
			if (entity instanceof XantisEntity) {
				((XantisEntity) entity).setAnimation("animation.xantis.tap");
			}
			if (entity instanceof XantisEntity _datEntSetI)
				_datEntSetI.getEntityData().set(XantisEntity.DATA_TAP_TICK, 9);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
