package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Comparator;
import net.minecraft.world.entity.Mob;

public class GetFellowAroundProcedure {
	public static final Vec3 offset = new Vec3(6,6,6);
	
	public static double execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return 0;
		Vec3 vec3 = new Vec3(x,y,z); 
        AABB aabb = new AABB(vec3.add(offset), vec3.add(offset.reverse()));
        List<Mob> fellow = world.getEntitiesOfClass(Mob.class, aabb, m -> !m.is(entity) && m.getType().equals(entity.getType()));
        return fellow.size();
	}
}
