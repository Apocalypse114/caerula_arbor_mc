package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.FlamarineGolemEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class FlamarineSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double dura = 0;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.deathTime : 0) == 46) {
			if (!world.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.hit_ground")), SoundSource.HOSTILE, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.hit_ground")), SoundSource.HOSTILE, 2, 1, false);
					}
				}
			}
		}
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof FlamarineGolemEntity _datEntI ? _datEntI.getEntityData().get(FlamarineGolemEntity.DATA_skillP1) : 0;
		sklp2 = entity instanceof FlamarineGolemEntity _datEntI ? _datEntI.getEntityData().get(FlamarineGolemEntity.DATA_skillP2) : 0;
		dura = entity instanceof FlamarineGolemEntity _datEntI ? _datEntI.getEntityData().get(FlamarineGolemEntity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof FlamarineGolemEntity _datEntSetI)
				_datEntSetI.getEntityData().set(FlamarineGolemEntity.DATA_duration, (int) (dura - 1));
		}
		if (sklp1 <= 0 && dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 6) {
					if (entity instanceof FlamarineGolemEntity) {
						((FlamarineGolemEntity) entity).setAnimation("animation.flamarine_golem.heavy");
					}
					if (entity instanceof FlamarineGolemEntity _datEntSetI)
						_datEntSetI.getEntityData().set(FlamarineGolemEntity.DATA_skillP1, 11);
					if (entity instanceof FlamarineGolemEntity _datEntSetI)
						_datEntSetI.getEntityData().set(FlamarineGolemEntity.DATA_duration, 40);
					dura = 40;
					CaerulaArborMod.queueServerWork(20, () -> {
						if (entity.isAlive()) {
							FlamarineRangedProcedure.execute(world, x, y, z, entity);
						}
					});
				}
			}
		}
		if (sklp2 > 0) {
			if (entity instanceof FlamarineGolemEntity _datEntSetI)
				_datEntSetI.getEntityData().set(FlamarineGolemEntity.DATA_skillP2, (int) (sklp2 - 1));
		} else if (dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 5) {
					if (entity instanceof FlamarineGolemEntity) {
						((FlamarineGolemEntity) entity).setAnimation("animation.flamarine_golem.combo");
					}
					if (entity instanceof FlamarineGolemEntity _datEntSetI)
						_datEntSetI.getEntityData().set(FlamarineGolemEntity.DATA_skillP2, 380);
					if (entity instanceof FlamarineGolemEntity _datEntSetI)
						_datEntSetI.getEntityData().set(FlamarineGolemEntity.DATA_duration, 60);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 30, 9, false, false));
					dura = 40;
					CaerulaArborMod.queueServerWork(22, () -> {
						if (entity.isAlive()) {
							FlamarineComboProcedure.execute(world, x, y, z, entity, 5.75, 2);
						}
					});
					CaerulaArborMod.queueServerWork(35, () -> {
						if (entity.isAlive()) {
							FlamarineComboProcedure.execute(world, x, y, z, entity, 6.5, 2.5);
						}
					});
				}
			}
		}
		if (entity.tickCount % 20 == 10) {
			if (!(enemy == null) && enemy.isAlive()) {
				FlamarineDestroyBlockProcedure.execute(world, x, y, z);
			}
		}
	}
}
