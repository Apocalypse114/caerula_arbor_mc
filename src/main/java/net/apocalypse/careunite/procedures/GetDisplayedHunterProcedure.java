package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.UlpiansEntity;
import net.apocalypse.careunite.entity.SpecterEntity;
import net.apocalypse.careunite.entity.SkadiEntity;
import net.apocalypse.careunite.entity.GladiiaEntity;

public class GetDisplayedHunterProcedure {
	public static Entity execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return null;
		String selection = "";
		selection = entity.getPersistentData().getString("centrifugerSelection");
		if ((selection).equals("skadi")) {
			return world instanceof Level _level ? new SkadiEntity(CaerulaArborModEntities.SKADI.get(), _level) : null;
		} else if ((selection).equals("ulpians")) {
			return world instanceof Level _level ? new UlpiansEntity(CaerulaArborModEntities.ULPIANS.get(), _level) : null;
		} else if ((selection).equals("gladiia")) {
			return world instanceof Level _level ? new GladiiaEntity(CaerulaArborModEntities.GLADIIA.get(), _level) : null;
		} else if ((selection).equals("specter")) {
			return world instanceof Level _level ? new SpecterEntity(CaerulaArborModEntities.SPECTER.get(), _level) : null;
		}
		return entity;
	}
}
