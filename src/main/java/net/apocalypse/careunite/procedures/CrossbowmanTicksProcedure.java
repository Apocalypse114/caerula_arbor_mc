package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.PenalBatallionCrossbowmanEntity;

import java.util.List;
import java.util.Comparator;

public class CrossbowmanTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp = 0;
		double less = 0;
		if (entity.isAlive()) {
			sklp = entity instanceof PenalBatallionCrossbowmanEntity _datEntI ? _datEntI.getEntityData().get(PenalBatallionCrossbowmanEntity.DATA_SKILLP) : 0;
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (sklp > -300) {
				if (sklp > 0) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 9, false, false));
				}
				if (entity instanceof PenalBatallionCrossbowmanEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PenalBatallionCrossbowmanEntity.DATA_SKILLP, (int) (sklp - 1));
			} else {
				if (!(enemy == null) && enemy.isAlive()) {
					if (entity instanceof PenalBatallionCrossbowmanEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PenalBatallionCrossbowmanEntity.DATA_SKILLP, 150);
				}
			}
			if (entity.tickCount % 40 == 10) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator == entity) {
							continue;
						}
						if (IsBatalionSuitProcedure.execute(entityiterator)) {
							less = less + 1;
						}
						if (less >= 6) {
							break;
						}
					}
				}
				if (less > 0) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 40, (int) (less * 10 - 1), false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.STRENGTH_OF_CROWD.get(), 40, (int) (less * 2 - 1), false, false));
				}
			}
		}
	}
}
