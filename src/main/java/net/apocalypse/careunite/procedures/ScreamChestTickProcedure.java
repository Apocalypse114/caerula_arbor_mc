package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.ScreamChestFishEntity;

public class ScreamChestTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double scream = 0;
		double d = 0;
		double angle = 0;
		double t = 0;
		if (!(entity instanceof ScreamChestFishEntity _datEntL0 && _datEntL0.getEntityData().get(ScreamChestFishEntity.DATA_release))) {
			entity.setShiftKeyDown(true);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 8, false, false));
		} else {
			if (entity.isAlive()) {
				entity.setShiftKeyDown(false);
				scream = entity instanceof ScreamChestFishEntity _datEntI ? _datEntI.getEntityData().get(ScreamChestFishEntity.DATA_SCREAM_TICK) : 0;
				if (scream > 0) {
					if (entity instanceof ScreamChestFishEntity _datEntSetI)
						_datEntSetI.getEntityData().set(ScreamChestFishEntity.DATA_SCREAM_TICK, (int) (scream - 1));
					t = entity.tickCount;
					if (scream % 10 == 0) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_shrieker.shriek")), SoundSource.HOSTILE, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_shrieker.shriek")), SoundSource.HOSTILE, 1, 1, false);
							}
						}
					}
					for (int index0 = 0; index0 < 60; index0++) {
						angle = Math.toRadians(index0 * 6 + t * 0.6);
						d = (t * 0.5) % 5;
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, (x + d * Math.sin(angle)), (y + 0.25), (z + d * Math.cos(angle)), 2, 0.1, 0.1, 0.1, 0.1);
					}
					for (Entity entityiterator : world.getEntities(entity, new AABB((x - 5), (y - 2), (z - 5), (x + 5), (y + 3), (z + 5)))) {
						if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 5) {
							if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
								if (!(entityiterator == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null))) {
									continue;
								}
							}
							if (!(entityiterator instanceof LivingEntity)) {
								continue;
							}
							DeductPlayerSanityProcedure.execute(entityiterator, 5);
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 1));
						}
					}
				}
			}
		}
	}
}
