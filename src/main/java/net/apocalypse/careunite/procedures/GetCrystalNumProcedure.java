package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

import java.util.List;
import java.util.Comparator;

public class GetCrystalNumProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity instanceof OceanizedEnderinaEntity enderina)
{
			return enderina.crystals.size();
		}
		return 0;
	}
}
