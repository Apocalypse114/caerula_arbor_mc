package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.PregnantFishEntity;

public class EntityPregnantProcedure {
	public static Entity execute(LevelAccessor world) {
		return world instanceof Level _level ? new PregnantFishEntity(CaerulaArborModEntities.PREGNANT_FISH.get(), _level) : null;
	}
}
