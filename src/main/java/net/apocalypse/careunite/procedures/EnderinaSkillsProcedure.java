package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class EnderinaSkillsProcedure {
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
		if (deadTime >= 30) {
			if (entity instanceof OceanizedEnderinaEntity animatable)
				animatable.setTexture("oceanized_enderina_3");
		} else if (deadTime >= 20) {
			if (entity instanceof OceanizedEnderinaEntity animatable)
				animatable.setTexture("oceanized_enderina_2");
		} else if (deadTime >= 10) {
			if (entity instanceof OceanizedEnderinaEntity animatable)
				animatable.setTexture("oceanized_enderina_1");
		}
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof OceanizedEnderinaEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEnderinaEntity.DATA_SKILL_P) : 0;
		dura = entity instanceof OceanizedEnderinaEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEnderinaEntity.DATA_DURATION) : 0;
		rev = entity instanceof OceanizedEnderinaEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEnderinaEntity.DATA_REVIVE_TICK) : 0;
		if (entity.tickCount % 100 == 0) {
			if (DistFromGroundProcedure.execute(world, x, y, z)) {
				entity.push(0, (-0.35), 0);
			}
		}
		P = entity instanceof OceanizedEnderinaEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEnderinaEntity.DATA_PHASE) : 0;
		if (rev > 0) {
			if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_REVIVE_TICK, (int) (dura - 1));
			entity.setShiftKeyDown(true);
			entity.setDeltaMovement(new Vec3(0, 0, 0));
			if (entity.tickCount % 10 == 0) {
				SwallowCrystalsProcedure.execute(world, x, y, z, entity);
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
				if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_REVIVE_TICK, 0);
			}
			if (Math.random() < 0.033) {
				DragonBreathRainProcedure.execute(world, x, y, z, entity);
			}
			if (P > 0.5) {
				if (Math.random() < 0.033) {
					DragonBreathRainProcedure.execute(world, x, y, z, entity);
				}
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
					if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_REVIVE_TICK, 0);
					if (entity instanceof OceanizedEnderinaEntity animatable)
						animatable.setTexture("oceanized_enderina_noise");
				}
				if (rev < 100) {
					if (entity instanceof OceanizedEnderinaEntity animatable)
						animatable.setTexture("oceanized_enderina_noise");
				}
			} else {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
					if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_REVIVE_TICK, 0);
				}
			}
		} else {
			entity.setShiftKeyDown(false);
		}
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_DURATION, (int) (dura - 1));
		}
		if (sklp1 > 0) {
			if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_SKILL_P, (int) (sklp1 - 1));
		} else if (dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if (entity instanceof OceanizedEnderinaEntity) {
					((OceanizedEnderinaEntity) entity).setAnimation("animation.oceanized_enderina.chant");
				}
				if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_SKILL_P, 370);
				if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_DURATION, 70);
				dura = 70;
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 50, 0, false, false));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_skill")), SoundSource.HOSTILE, (float) 2.5, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_skill")), SoundSource.HOSTILE, (float) 2.5, 1, false);
					}
				}
				for (int index0 = 0; index0 < 8; index0++) {
					CaerulaArborMod.queueServerWork((int) 12 + index0 * 5, () -> {
						if (entity.isAlive()) {
							CastDragonBreathProcedure.execute(world, entity.getX(), entity.getY() + 3, entity.getZ(), entity, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null, Mth.nextInt(RandomSource.create(), 0, 1));
						}
					});
					if (P > 0.5) {
						CaerulaArborMod.queueServerWork((int) 14 + index0 * 5, () -> {
							if (entity.isAlive()) {
								CastDragonBreathProcedure.execute(world, entity.getX(), entity.getY() + 3, entity.getZ(), entity, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null, Mth.nextInt(RandomSource.create(), 0, 1));
							}
						});
						CaerulaArborMod.queueServerWork((int) 15 + index0 * 5, () -> {
							if (entity.isAlive()) {
								CastDragonBreathProcedure.execute(world, entity.getX(), entity.getY() + 3, entity.getZ(), entity, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null, Mth.nextInt(RandomSource.create(), 0, 1));
							}
						});
					}
				}
			}
		}
		if (entity.tickCount % 20 == 10) {
			if (!(enemy == null) && enemy.isAlive()) {
				WitheriaDestoryBlockProcedure.execute(world, x, y, z);
			}
		}
		if (entity.tickCount % 400 == 100) {
			for (int index1 = 0; index1 < 2; index1++) {
				DistributeCrystalProcedure.execute(world, x, y, z, entity);
			}
		}
		if (entity.tickCount % 20 == 0) {
			HandleCrystalsProcedure.execute(world, x, y, z, entity);
		}
		if (rev > 0 && entity.tickCount % 70 == 50) {
			DistributeCrystalProcedure.execute(world, x, y, z, entity);
		}
		if (GetSpeedProcedure.execute(entity) > 0.64) {
			entity.setDeltaMovement(new Vec3(0, 0, 0));
		}
	}
}
