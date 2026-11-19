package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.RouteShaperEntity;
import net.apocalypse.careunite.entity.RouteFractalEntity;
import net.apocalypse.careunite.entity.LineringPathshaperEntity;

import java.util.List;
import java.util.Comparator;

public class BoostFracsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.tickCount % 20 == 7) {
			if ((entity instanceof RouteShaperEntity _datEntI ? _datEntI.getEntityData().get(RouteShaperEntity.DATA_phase) : 0) == 1 || entity instanceof LineringPathshaperEntity) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof RouteFractalEntity) {
							if (!(entityiterator instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(CaerulaArborModMobEffects.SEEK_OF_FRACTAL.get()))) {
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SEEK_OF_FRACTAL.get(), 999, 0));
							}
						}
					}
				}
			}
		}
	}
}
