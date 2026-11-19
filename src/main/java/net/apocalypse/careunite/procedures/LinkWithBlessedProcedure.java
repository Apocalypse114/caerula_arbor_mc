package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class LinkWithBlessedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double num = 0;
		double limit = 0;
		limit = 2;
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting > 3) {
			limit = 3;
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(96 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof Mob)) {
					continue;
				}
				if (entityiterator instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.GUIDED_EVO.get())) {
					num = num + 1;
					ParticleLinkProcedure.execute(world, entity, entityiterator);
					CaerulaArborMod.queueServerWork(3, () -> {
						ParticleLinkProcedure.execute(world, entity, entityiterator);
					});
					CaerulaArborMod.queueServerWork(6, () -> {
						ParticleLinkProcedure.execute(world, entity, entityiterator);
					});
					CaerulaArborMod.queueServerWork(9, () -> {
						ParticleLinkProcedure.execute(world, entity, entityiterator);
					});
				}
				if (num >= limit) {
					break;
				}
			}
		}
	}
}
