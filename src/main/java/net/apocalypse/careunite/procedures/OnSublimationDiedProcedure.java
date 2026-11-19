package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.IzumikOffspringEntity;
import net.apocalypse.careunite.entity.CaerulaOffspringEntity;

public class OnSublimationDiedProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		double finalBreed = 0;
		double rate1 = 0;
		double healthRate = 0;
		double rate2 = 0;
		if (IsSeabornBossProcedure.execute(entity)) {
			return false;
		}
		if (entity.getPersistentData().getBoolean("sublimationReborn")) {
			return false;
		}
		if (entity instanceof IzumikOffspringEntity || entity instanceof CaerulaOffspringEntity) {
			return false;
		}
		finalBreed = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation, CaerulaArborModVariables.MapVariables.get(world).strategy_breed);
		if (finalBreed > 0) {
			rate1 = 0.05 + 0.05 * finalBreed;
			healthRate = 0.1 + 0.05 * finalBreed;
			if (Math.random() < rate1) {
				if (!world.isClientSide()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.HOSTILE, (float) 1.5, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.HOSTILE, (float) 1.5, 1, false);
						}
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, x, (y + 1), z, 32, 1, 1, 1, 0.15);
				}
				entity.getPersistentData().putBoolean("sublimationReborn", true);
				if (finalBreed == 3) {
					rate2 = 0.1;
				} else if (finalBreed == 4) {
					rate2 = 0.2;
				}
				if (Math.random() < rate2) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = CaerulaArborModEntities.CAERULA_OFFSPRING.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				}
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * healthRate));
				return true;
			}
		}
		return false;
	}
}
