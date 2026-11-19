package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.entity.RunvolutionEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class RunvolutionSKillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double dura = 0;
		double P = 0;
		double sklp1 = 0;
		double rev = 0;
		double deadTime = 0;
		deadTime = entity instanceof LivingEntity _livEnt ? _livEnt.deathTime : 0;
		if (deadTime == 20 || deadTime == 25 || deadTime == 30 || deadTime == 35) {
			if (deadTime == 35) {
				DropRunvolutionCoreProcedure.execute(world, x, y, z);
			}
			DropRunvolutionExtraLootProcedure.execute(world, x, y, z);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.EXPLOSION, x, (y + 2.5), z, 3, 0.5, 1, 0.5, 0.1);
		}
		if (!entity.isAlive()) {
			return;
		}
		if (entity.tickCount % 3 == 0) {
			RUnvolutionIconShowProcedure.execute(world, x, y, z, entity);
		}
		entity.stopRiding();
		sklp1 = entity instanceof RunvolutionEntity _datEntI ? _datEntI.getEntityData().get(RunvolutionEntity.DATA_SKILL_P) : 0;
		dura = entity instanceof RunvolutionEntity _datEntI ? _datEntI.getEntityData().get(RunvolutionEntity.DATA_DURATION) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof RunvolutionEntity _datEntSetI)
				_datEntSetI.getEntityData().set(RunvolutionEntity.DATA_DURATION, (int) (dura - 1));
		}
		if (GetSeabornNumProcedure.execute(world, x, y, z) < (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT)) * 3) {
			if (IsRunvoltuionInPhaseProcedure.execute(entity) && entity.tickCount % 40 == 15) {
				RunvolutionSummonRProcedure.execute(world, entity, x, y, z);
			} else if (GetHealthPercProcedure.execute(entity) <= 0.5 && entity.tickCount % 60 == 45 || GetHealthPercProcedure.execute(entity) > 0.5 && entity.tickCount % 100 == 75) {
				RunvolutionSummonRProcedure.execute(world, entity, x, y, z);
			}
		}
		if (sklp1 > 0) {
			if (entity instanceof RunvolutionEntity _datEntSetI)
				_datEntSetI.getEntityData().set(RunvolutionEntity.DATA_SKILL_P, (int) (sklp1 - 1));
		} else if (dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if (entity instanceof RunvolutionEntity) {
					((RunvolutionEntity) entity).setAnimation("animation.runvolution.skill");
				}
				if (IsRunvoltuionInPhaseProcedure.execute(entity)) {
					if (entity instanceof RunvolutionEntity _datEntSetI)
						_datEntSetI.getEntityData().set(RunvolutionEntity.DATA_SKILL_P, 140);
					if (GetHealthPercProcedure.execute(entity) > 0.05) {
						if (entity instanceof LivingEntity _entity)
							_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * (GetHealthPercProcedure.execute(entity) - 0.05)));
					}
				} else if (GetHealthPercProcedure.execute(entity) <= 0.5) {
					if (entity instanceof RunvolutionEntity _datEntSetI)
						_datEntSetI.getEntityData().set(RunvolutionEntity.DATA_SKILL_P, 240);
				} else {
					if (entity instanceof RunvolutionEntity _datEntSetI)
						_datEntSetI.getEntityData().set(RunvolutionEntity.DATA_SKILL_P, 440);
				}
				if (entity instanceof RunvolutionEntity _datEntSetI)
					_datEntSetI.getEntityData().set(RunvolutionEntity.DATA_DURATION, 22);
				dura = 22;
				CaerulaArborMod.queueServerWork(10, () -> {
					if (entity.isAlive()) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_release")), SoundSource.HOSTILE, 3, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_release")), SoundSource.HOSTILE, 3, 1, false);
							}
						}
						RunvolutionThrowWaveProcedure.execute(world, x, y, z);
					}
				});
			}
		}
		if (AboveFromGroundProcedure.execute(world, x, y, z)) {
			entity.setDeltaMovement(new Vec3(0, (entity.getDeltaMovement().y()), 0));
		} else {
			entity.setDeltaMovement(new Vec3(0, 0, 0));
		}
	}
}
