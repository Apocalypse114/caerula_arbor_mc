package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.AccumulatorProkaryoteEntity;
import net.minecraft.sounds.SoundEvents;

public class SummonHoarderCloneProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level){
			BlockPos pos = entity.blockPosition();
			Entity entityToSpawn;
			if (entity.isInWater()) {
				entityToSpawn = CaerulaArborModEntities.ACCUMULATOR_PROKARYOTE.get().spawn(_level,
 pos, MobSpawnType.MOB_SUMMONED);
			} else {
				entityToSpawn = CaerulaArborModEntities.DIVICELLULAR_GO.get().spawn(_level,
 pos, MobSpawnType.MOB_SUMMONED);
			}
			if (entityToSpawn != null) entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			if (entityToSpawn instanceof AccumulatorProkaryoteEntity _entity){
				_entity.setHealth((float) (_entity.getMaxHealth() * 0.5));
				_entity.getEntityData().set(AccumulatorProkaryoteEntity.DATA_split, false);
				EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
			}
			_level.playSound(null, pos, SoundEvents.PUFFER_FISH_BLOW_OUT, SoundSource.HOSTILE, 2, 1);
		}
		
	}
}
