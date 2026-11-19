package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TideDeathrepellerEntity;
import net.apocalypse.careunite.entity.TideBishopEntity;
import net.apocalypse.careunite.entity.ThirsterEntity;
import net.apocalypse.careunite.entity.RaidSignEntity;
import net.apocalypse.careunite.entity.OceanizedSnifferEntity;
import net.apocalypse.careunite.entity.OceanizedSnifferEggEntity;
import net.apocalypse.careunite.entity.OceanizedIllusionerEntity;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;
import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;
import net.apocalypse.careunite.entity.MartusEntity;
import net.apocalypse.careunite.entity.Endspeaker2Entity;
import net.apocalypse.careunite.entity.Endspeaker1Entity;
import net.apocalypse.careunite.entity.Endspeaker0Entity;
import net.apocalypse.careunite.entity.AbsorberLimbEntity;

import java.util.Comparator;

public class GetGoalAfterChangeProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity curGoal, Entity entity) {
		if (curGoal == null || entity == null)
			return null;
		Entity other = null;
		Entity enemy = null;
		enemy = curGoal;
		other = curGoal;
		if (enemy instanceof TideDeathrepellerEntity && enemy instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get())) {
			other = (Entity) world.getEntitiesOfClass(TideBishopEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
		} else if (enemy instanceof TideBishopEntity && enemy instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get())) {
			other = (Entity) world.getEntitiesOfClass(TideDeathrepellerEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
		} else if (enemy instanceof MartusEntity && enemy instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get())) {
			other = GetLinkedSeabornProcedure.execute(world, x, y, z, entity);
		} else if ((enemy instanceof Endspeaker0Entity || enemy instanceof Endspeaker1Entity || enemy instanceof Endspeaker2Entity) && enemy instanceof LivingEntity _livEnt11 && _livEnt11.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get())) {
			other = GetNearestSeabornProcedure.execute(world, x, y, z, enemy);
		} else if (enemy instanceof OceanizedIllusionerEntity) {
			other = GetNearsetIllusionerProcedure.execute(world, x, y, z);
		} else if (enemy instanceof OceanizedEnderinaEntity && !IsEnderinaDurativeProcedure.execute(entity)) {
			other = (Entity) world.getEntitiesOfClass(MoistEnderCrystalEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
		} else if (enemy instanceof OceanizedSnifferEntity) {
			other = (Entity) world.getEntitiesOfClass(OceanizedSnifferEggEntity.class, AABB.ofSize(new Vec3(x, y, z), 40, 40, 40), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
		} else if (enemy instanceof ThirsterEntity) {
			other = (Entity) world.getEntitiesOfClass(AbsorberLimbEntity.class, AABB.ofSize(new Vec3(x, y, z), 46, 46, 46), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
		} else if (enemy instanceof RaidSignEntity) {
			other = RaiderFromSignProcedure.execute(enemy);
		}
		return other;
	}
}
