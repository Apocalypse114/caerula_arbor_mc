package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.TideDeathrepellerEntity;
import net.apocalypse.careunite.entity.TideBishopEntity;

import java.util.Comparator;

public class TideBiDeathProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean keepup = false;
		Entity nearest = null;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get())) {
			keepup = true;
			if (entity instanceof TideDeathrepellerEntity) {
				nearest = (Entity) world.getEntitiesOfClass(TideBishopEntity.class, AABB.ofSize(new Vec3(x, y, z), 128, 128, 128), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null);
			}
			if (entity instanceof TideBishopEntity) {
				keepup = true;
				nearest = (Entity) world.getEntitiesOfClass(TideDeathrepellerEntity.class, AABB.ofSize(new Vec3(x, y, z), 128, 128, 128), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null);
			}
			if (nearest == null) {
				keepup = false;
			} else if (nearest instanceof LivingEntity _livEnt6 && _livEnt6.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get())) {
				keepup = false;
			}
			if (!keepup) {
				if (entity instanceof LivingEntity _entity)
					_entity.removeAllEffects();
				MakeEntityDieProcedure.execute(world, entity);
			}
		} else {
			if (entity instanceof TideDeathrepellerEntity) {
				RepellerSkillProcedure.execute(world, x, y, z, entity);
				nearest = (Entity) world.getEntitiesOfClass(TideBishopEntity.class, AABB.ofSize(new Vec3(x, y, z), 128, 128, 128), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null);
				if (!(nearest == null) && nearest instanceof LivingEntity _livEnt11 && _livEnt11.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get())) {
					LinkBetweenProcedure.execute(world, entity, nearest);
					if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 3) {
						if (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
							_livingEntity12.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(40);
					} else if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting >= 4) {
						if (entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
							_livingEntity13.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(20);
					}
				}
			}
			if (entity instanceof TideBishopEntity) {
				BishopSkillProcedure.execute(world, x, y, z, entity);
				nearest = (Entity) world.getEntitiesOfClass(TideDeathrepellerEntity.class, AABB.ofSize(new Vec3(x, y, z), 128, 128, 128), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null);
				if (!(nearest == null) && nearest instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get())) {
					LinkBetweenProcedure.execute(world, entity, nearest);
					if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 3) {
						if (entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
							_livingEntity18.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(30);
					} else if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting >= 4) {
						if (entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
							_livingEntity19.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(15);
					}
				} else {
					if (entity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
						_livingEntity20.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(0);
				}
			}
			entity.setShiftKeyDown(false);
		}
	}
}
