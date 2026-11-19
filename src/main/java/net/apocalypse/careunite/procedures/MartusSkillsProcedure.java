package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.MartusEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class MartusSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity tgt = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double phase = 0;
		double perc = 0;
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof MartusEntity _datEntI ? _datEntI.getEntityData().get(MartusEntity.DATA_skillp1) : 0;
		sklp2 = entity instanceof MartusEntity _datEntI ? _datEntI.getEntityData().get(MartusEntity.DATA_skillp2) : 0;
		phase = entity instanceof MartusEntity _datEntI ? _datEntI.getEntityData().get(MartusEntity.DATA_phase) : 0;
		if (entity.tickCount % 100 == 0) {
			if (DistFromGroundProcedure.execute(world, x, y, z)) {
				entity.push(0, (-0.64), 0);
			}
		}
		if (phase < 0.33) {
			if (entity.tickCount % 2 == 0) {
				MartusParticleRimProcedure.execute(world, x, y, z);
			}
			if (!(entity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 10000, 9, false, false));
			}
			if (sklp1 > 0) {
				if (entity instanceof MartusEntity _datEntSetI)
					_datEntSetI.getEntityData().set(MartusEntity.DATA_skillp1, (int) (sklp1 - 1));
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting > 3) {
					if (entity instanceof MartusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(MartusEntity.DATA_skillp1, (int) (sklp1 - 1));
				}
			} else {
				if (entity.tickCount % 10 == 0) {
					tgt = GetMostHealthEntProcedure.execute(world, x, y, z, entity);
					DoMartusBuffProcedure.execute(entity, tgt);
				}
			}
			if (entity.tickCount % 300 == 0) {
				MartusTimedSpawnProcedure.execute(world, x, y, z, entity);
			}
		} else {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.33) {
				if (!(entity instanceof LivingEntity _livEnt15 && _livEnt15.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 20, 9, false, false));
				}
				if (entity.tickCount % 2 == 0) {
					MartusParticleRimProcedure.execute(world, x, y, z);
				}
			}
			if (sklp1 > 0) {
				if (entity instanceof MartusEntity _datEntSetI)
					_datEntSetI.getEntityData().set(MartusEntity.DATA_skillp1, (int) (sklp1 - 1));
			} else {
				if (entity.tickCount % 10 == 0 && GetSeabornAroundProcedure.execute(world, x, y, z, entity) > 0) {
					if (entity instanceof MartusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(MartusEntity.DATA_skillp1, 1000);
					if (entity instanceof MartusEntity) {
						((MartusEntity) entity).setAnimation("animation.martus.cure");
					}
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.02) {
								HurtMartusProcedure.execute(world, entity, null, 0, 0.02);
							}
							final int tick2 = ticks;
							CaerulaArborMod.queueServerWork(tick2, () -> {
								if (timedlooptotal > timedloopiterator + 1) {
									timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
								}
							});
						}
					}.timedLoop(0, 10, 20);
					CaerulaArborMod.queueServerWork(10, () -> {
						MartusHealAllProcedure.execute(world, x, y, z, entity);
					});
				}
			}
			if (sklp2 > 0) {
				if (entity instanceof MartusEntity _datEntSetI)
					_datEntSetI.getEntityData().set(MartusEntity.DATA_skillp2, (int) (sklp2 - 1));
			} else {
				if (entity instanceof MartusEntity _datEntSetI)
					_datEntSetI.getEntityData().set(MartusEntity.DATA_skillp2, 600);
				if (entity instanceof MartusEntity) {
					((MartusEntity) entity).setAnimation("animation.martus.reject");
				}
				CaerulaArborMod.queueServerWork(15, () -> {
					SelectHaemoTargetProcedure.execute(world, x, y, z, entity);
				});
			}
			if (entity.tickCount % 200 == 0) {
				MartusTimedSpawnProcedure.execute(world, x, y, z, entity);
			}
		}
	}
}
