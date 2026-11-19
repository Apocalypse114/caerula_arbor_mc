package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.CreeperFishEntity;

public class EntityCreeperProcedure {
	public static Entity execute(LevelAccessor world) {
		return world instanceof Level _level ? new CreeperFishEntity(CaerulaArborModEntities.CREEPER_FISH.get(), _level) : null;
	}
}
