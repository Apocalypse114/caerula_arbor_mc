package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class ChimeraBlastProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		Entity entityToSpawn = null;
		BlockPos pos = BlockPos.containing(x, y, z);
		if (world instanceof ServerLevel _level) {
			entityToSpawn = CaerulaArborModEntities.CRACKER_ABYSSAL.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.GUIDE_ABYSSAL.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.UMBRELLA_ABYSSAL.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.CREEPER_FISH.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.PREGNANT_FISH.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.BASELAYER_ABYSSAL.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.PREDATOR_ABYSSAL.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.SPLASHER_ABYSSAL.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.APOSTLE_PROKARYOTE.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.DIVICELLULAR_GO.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.PUNCTURE_FISH.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
			entityToSpawn = CaerulaArborModEntities.NUCLEIC_MALEFICENT.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
		}
	}
}
