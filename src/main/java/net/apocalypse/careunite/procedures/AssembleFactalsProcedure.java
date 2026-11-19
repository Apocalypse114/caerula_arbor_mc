package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.RouteShaperEntity;
import net.apocalypse.careunite.entity.RouteFractalEntity;
import net.apocalypse.careunite.entity.LingeringFractalEntity;
import net.apocalypse.careunite.entity.LineringPathshaperEntity;

import java.util.List;
import java.util.Comparator;

public class AssembleFactalsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double sklp = 0;
		boolean isLingering = false;
		isLingering = entity instanceof LineringPathshaperEntity;
		if (isLingering) {
			sklp = entity instanceof LineringPathshaperEntity _datEntI ? _datEntI.getEntityData().get(LineringPathshaperEntity.DATA_skillp) : 0;
		} else {
			sklp = entity instanceof RouteShaperEntity _datEntI ? _datEntI.getEntityData().get(RouteShaperEntity.DATA_skillp) : 0;
		}
		if (sklp >= 8) {
			SummonFractalProcedure.execute(world, x, y, z, entity);
			if (Math.random() < 0.33) {
				SummonFractalProcedure.execute(world, x, y, z, entity);
			}
			if (entity instanceof RouteShaperEntity _datEntSetI)
				_datEntSetI.getEntityData().set(RouteShaperEntity.DATA_skillp, 0);
			if (entity instanceof LineringPathshaperEntity _datEntSetI)
				_datEntSetI.getEntityData().set(LineringPathshaperEntity.DATA_skillp, 0);
		} else {
			if (entity instanceof RouteShaperEntity _datEntSetI)
				_datEntSetI.getEntityData().set(RouteShaperEntity.DATA_skillp, (int) (sklp + 2));
			if (entity instanceof LineringPathshaperEntity _datEntSetI)
				_datEntSetI.getEntityData().set(LineringPathshaperEntity.DATA_skillp, (int) (sklp + 2));
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof RouteFractalEntity || entityiterator instanceof LingeringFractalEntity) {
					if (entityiterator instanceof Mob _entity && sourceentity instanceof LivingEntity _ent)
						_entity.setTarget(_ent);
				}
			}
		}
	}
}
