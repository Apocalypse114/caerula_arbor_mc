package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

import java.util.Set;
import java.util.HashSet;

public class HandleCrystalsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity instanceof OceanizedEnderinaEntity enderina)
{
			Set<String> _entfound = enderina.crystals, toDelete = new HashSet<>();
			_entfound.forEach(s -> {
				MoistEnderCrystalEntity entityiterator = enderina.getCrystal(s);
				if (entityiterator != null && entityiterator.isAlive()){
					CrystalRoundOwnerProcedure.execute(entityiterator, entity);
				} else {
					toDelete.add(s);
				}
			});
			enderina.crystals.removeAll(toDelete);
		}
	}
}
