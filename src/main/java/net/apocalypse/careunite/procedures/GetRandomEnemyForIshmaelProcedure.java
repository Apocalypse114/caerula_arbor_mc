package net.apocalypse.careunite.procedures;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GetRandomEnemyForIshmaelProcedure {
	public static Entity execute(LevelAccessor level, double x, double y, double z, Entity entity) {
		Vec3 pos = entity.position();
        AABB area = new AABB(pos.add(-24,-16,-24),pos.add(24,16,24));
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, e->{
            return IsValidEnemyForIshmaelProcedure.execute(e, entity)
            && e.position().vectorTo(pos).horizontalDistanceSqr()<= 576;
        });
        if(entities.size() <= 0) return null;
        int index = Mth.nextInt(level.getRandom(),0, entities.size() - 1);
        return entities.get(index);
	}
}
