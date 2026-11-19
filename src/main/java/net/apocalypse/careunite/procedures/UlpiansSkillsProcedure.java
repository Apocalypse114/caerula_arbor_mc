package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.UlpiansEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class UlpiansSkillsProcedure {
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
		sklp1 = entity instanceof UlpiansEntity _datEntI ? _datEntI.getEntityData().get(UlpiansEntity.DATA_skillp1) : 0;
		skillp2 = entity instanceof UlpiansEntity _datEntI ? _datEntI.getEntityData().get(UlpiansEntity.DATA_skillp2) : 0;
		dura = entity instanceof UlpiansEntity _datEntI ? _datEntI.getEntityData().get(UlpiansEntity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof UlpiansEntity _datEntSetI)
				_datEntSetI.getEntityData().set(UlpiansEntity.DATA_duration, (int) (dura - 1));
		}
		if (sklp1 > 0) {
			if (entity instanceof UlpiansEntity _datEntSetI)
				_datEntSetI.getEntityData().set(UlpiansEntity.DATA_skillp1, (int) (sklp1 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 3.5) {
					if (entity instanceof UlpiansEntity) {
						((UlpiansEntity) entity).setAnimation("animation.ulpians.pull");
					}
					if (entity instanceof UlpiansEntity _datEntSetI)
						_datEntSetI.getEntityData().set(UlpiansEntity.DATA_skillp1, 120);
					if (entity instanceof UlpiansEntity _datEntSetI)
						_datEntSetI.getEntityData().set(UlpiansEntity.DATA_duration, (int) (dura + 40));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_pul_pre")), SoundSource.NEUTRAL, (float) 2.2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_pul_pre")), SoundSource.NEUTRAL, (float) 2.2, 1, false);
						}
					}
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + 1.6), (enemy.getZ())));
					CaerulaArborMod.queueServerWork(13, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_pull_throw")), SoundSource.NEUTRAL, 3, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_pull_throw")), SoundSource.NEUTRAL, 3, 1, false);
								}
							}
						}
					});
					CaerulaArborMod.queueServerWork(20, () -> {
						if (entity.isAlive()) {
							UlpiansAnchorRangedProcedure.execute(world, entity, 2.7);
						}
					});
					CaerulaArborMod.queueServerWork(24, () -> {
						if (entity.isAlive()) {
							UlpiansRangedPullProcedure.execute(world, x, y, z, entity);
						}
					});
					CaerulaArborMod.queueServerWork(26, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_pull_pull")), SoundSource.NEUTRAL, (float) 2.5, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_pull_pull")), SoundSource.NEUTRAL, (float) 2.5, 1, false);
								}
							}
						}
					});
				}
			}
		}
		if (skillp2 > 0) {
			if (entity instanceof UlpiansEntity _datEntSetI)
				_datEntSetI.getEntityData().set(UlpiansEntity.DATA_skillp2, (int) (skillp2 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 24) {
					if (entity instanceof UlpiansEntity) {
						((UlpiansEntity) entity).setAnimation("animation.ulpians.skill");
					}
					if (IsSpecterAroundProcedure.execute(world, x, y, z)) {
						if (entity instanceof UlpiansEntity _datEntSetI)
							_datEntSetI.getEntityData().set(UlpiansEntity.DATA_skillp2, 820);
					} else {
						if (entity instanceof UlpiansEntity _datEntSetI)
							_datEntSetI.getEntityData().set(UlpiansEntity.DATA_skillp2, 900);
					}
					if (entity instanceof UlpiansEntity _datEntSetI)
						_datEntSetI.getEntityData().set(UlpiansEntity.DATA_duration, (int) (dura + 40));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_pul_pre")), SoundSource.NEUTRAL, (float) 2.2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_pul_pre")), SoundSource.NEUTRAL, (float) 2.2, 1, false);
						}
					}
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + 1.6), (enemy.getZ())));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 25, 9, false, false));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_skill")), SoundSource.NEUTRAL, (float) 2.5, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_skill")), SoundSource.NEUTRAL, (float) 2.5, 1, false);
						}
					}
					CaerulaArborMod.queueServerWork(16, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:anchor_throw")), SoundSource.NEUTRAL, (float) 2.2, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:anchor_throw")), SoundSource.NEUTRAL, (float) 2.2, 1, false);
							}
						}
					});
					CaerulaArborMod.queueServerWork(22, () -> {
						if (entity.isAlive()) {
							UlpiansAnchorHitProcedure.execute(world, entity);
						}
					});
				}
			}
		}
		HealFromGladiiaProcedure.execute(world, x, y, z, entity);
	}
}
