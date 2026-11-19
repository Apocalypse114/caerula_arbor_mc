package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

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

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedEndermanEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class EndermanLaserSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp = 0;
		double cool = 0;
		if (entity instanceof OceanizedEndermanEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedEndermanEntity.DATA_HOLDING_CrEEPER)) {
			if (entity instanceof OceanizedEndermanEntity animatable)
				animatable.setTexture("oceanzied_enderman_creeper");
		} else {
			if (entity instanceof OceanizedEndermanEntity animatable)
				animatable.setTexture("oceanzied_enderman");
		}
		if (entity.isAlive()) {
			sklp = entity instanceof OceanizedEndermanEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEndermanEntity.DATA_skillp) : 0;
			cool = entity instanceof OceanizedEndermanEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEndermanEntity.DATA_cooldown) : 0;
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(enemy == null) && enemy.isAlive()) {
				if (sklp <= 0) {
					if (entity instanceof OceanizedEndermanEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedEndermanEntity.DATA_skillp, 300);
					if (entity instanceof OceanizedEndermanEntity) {
						((OceanizedEndermanEntity) entity).setAnimation("animation.oceanzied_enderman.skill");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 35, 0, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 35, 9, false, false));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.stare")), SoundSource.HOSTILE, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.stare")), SoundSource.HOSTILE, 1, 1, false);
						}
					}
					CaerulaArborMod.queueServerWork(13, () -> {
						EndermnLaserPreProcedure.execute(world, x, y, z, entity);
					});
				} else {
					if (entity instanceof OceanizedEndermanEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedEndermanEntity.DATA_skillp, (int) (sklp - 1));
				}
				if (cool <= 0 && enemy.isAlive()) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) >= 8 && (enemy != null ? entity.distanceTo(enemy) : -1) <= 64
							&& !(entity instanceof LivingEntity _livEnt19 && _livEnt19.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get()))) {
						if (entity instanceof OceanizedEndermanEntity _datEntSetI)
							_datEntSetI.getEntityData().set(OceanizedEndermanEntity.DATA_cooldown, 100);
						TeleportToProcedure.execute(world, entity, x, y, z, enemy.getX(), enemy.getY(), enemy.getZ());
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 100, 0, false, false));
					}
				} else {
					if (entity instanceof OceanizedEndermanEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedEndermanEntity.DATA_cooldown, (int) (cool - 1));
				}
			} else {
				EndermnaPickCreeperProcedure.execute(world, x, y, z, entity);
			}
		}
	}
}
