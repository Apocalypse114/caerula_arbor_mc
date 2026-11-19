package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.entity.IzumikEntity;

public class GiveFireResisProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amplifier) {
		if (entity == null)
			return;
		double ang = 0;
		double phase = 0;
		entity.invulnerableTime = 10;
		ang = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		if (amplifier > 4) {
			return;
		}
		if (amplifier == 0) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.INV_PTC_BLUE.get()), (x + 2 * Math.sin(ang)), (y + 1.25), (z + 2 * Math.cos(ang)), 1, 0.1, 2, 0.1, 0.2);
		} else if (amplifier == 1) {
			if (RecogEndspeakerProcedure.execute(entity)) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ENDSPEAKER_INV.get()), (x + 2 * Math.sin(ang)), (y + 1.25), (z + 2 * Math.cos(ang)), 4, 0.1, 2, 0.1, 0.2);
			} else {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.INV_PTC.get()), (x + 2 * Math.sin(ang)), (y + 1.25), (z + 2 * Math.cos(ang)), 4, 0.1, 2, 0.1, 0.2);
			}
		} else {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.INV_PTC_VOILET.get()), (x + 2 * Math.sin(ang)), (y + 1.25), (z + 2 * Math.cos(ang)), 4, 0.1, 2, 0.1, 0.2);
			if (entity instanceof IzumikEntity && (entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0) == 0) {
				phase = Math.floor((entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_growth_p) : 0) / 5);
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * (0.4 + phase * 0.15)));
			}
		}
	}
}
