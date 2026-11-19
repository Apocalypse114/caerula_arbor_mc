package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.GladiiaEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class GladiiaSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double gap = 0;
		double sklp1 = 0;
		double dura = 0;
		double skillp2 = 0;
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof GladiiaEntity _datEntI ? _datEntI.getEntityData().get(GladiiaEntity.DATA_skillP) : 0;
		skillp2 = entity instanceof GladiiaEntity _datEntI ? _datEntI.getEntityData().get(GladiiaEntity.DATA_skillP2) : 0;
		dura = entity instanceof GladiiaEntity _datEntI ? _datEntI.getEntityData().get(GladiiaEntity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof GladiiaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(GladiiaEntity.DATA_duration, (int) (dura - 1));
		}
		if (sklp1 > 0) {
			if (entity instanceof GladiiaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(GladiiaEntity.DATA_skillP, (int) (sklp1 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 7.5 && dura <= 0) {
					if (entity instanceof GladiiaEntity) {
						((GladiiaEntity) entity).setAnimation("animation.gladiia.pull");
					}
					if (entity instanceof GladiiaEntity _datEntSetI)
						_datEntSetI.getEntityData().set(GladiiaEntity.DATA_skillP, 160);
					if (entity instanceof GladiiaEntity _datEntSetI)
						_datEntSetI.getEntityData().set(GladiiaEntity.DATA_duration, 30);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_pull_pre")), SoundSource.NEUTRAL, (float) 2.5, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_pull_pre")), SoundSource.NEUTRAL, (float) 2.5, 1, false);
						}
					}
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + 1.6), (enemy.getZ())));
					CaerulaArborMod.queueServerWork(10, () -> {
						if (entity.isAlive()) {
							GladiiaDuelPullProcedure.execute(world, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null, entity);
						}
					});
				}
			}
		}
		if (skillp2 > 0) {
			if (entity instanceof GladiiaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(GladiiaEntity.DATA_skillP2, (int) (skillp2 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 21 && dura <= 0) {
					if (entity instanceof GladiiaEntity) {
						((GladiiaEntity) entity).setAnimation("animation.gladiia.float");
					}
					if (IsSpecterAroundProcedure.execute(world, x, y, z)) {
						if (entity instanceof GladiiaEntity _datEntSetI)
							_datEntSetI.getEntityData().set(GladiiaEntity.DATA_skillP2, 400);
					} else {
						if (entity instanceof GladiiaEntity _datEntSetI)
							_datEntSetI.getEntityData().set(GladiiaEntity.DATA_skillP2, 500);
					}
					if (entity instanceof GladiiaEntity _datEntSetI)
						_datEntSetI.getEntityData().set(GladiiaEntity.DATA_duration, 120);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_skill_release")), SoundSource.NEUTRAL, 3, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_skill_release")), SoundSource.NEUTRAL, 3, 1, false);
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_skill")), SoundSource.NEUTRAL, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_skill")), SoundSource.NEUTRAL, 2, 1, false);
						}
					}
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + 1.6), (enemy.getZ())));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 120, 4, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 120, 9, false, false));
					if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 3, false, false));
					DoSummonMyWhirlProcedure.execute(world, enemy.getX(), enemy.getY(), enemy.getZ(), entity);
					CaerulaArborMod.queueServerWork(111, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_pull_pre")), SoundSource.NEUTRAL, (float) 2.5, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_pull_pre")), SoundSource.NEUTRAL, (float) 2.5, 1, false);
								}
							}
						}
					});
					CaerulaArborMod.queueServerWork(114, () -> {
						if (entity.isAlive()) {
							GladiiaRangedPullProcedure.execute(world, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null, entity);
						}
					});
				}
			}
		}
		HealFromGladiiaProcedure.execute(world, x, y, z, entity);
	}
}
