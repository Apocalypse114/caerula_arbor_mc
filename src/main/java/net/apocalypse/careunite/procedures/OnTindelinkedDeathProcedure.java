package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TideDeathrepellerEntity;
import net.apocalypse.careunite.entity.TideBishopEntity;

import java.util.Comparator;

public class OnTindelinkedDeathProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		boolean keepup = false;
		Entity another = null;
		if (entity instanceof TideDeathrepellerEntity) {
			keepup = true;
			another = (Entity) world.getEntitiesOfClass(TideBishopEntity.class, AABB.ofSize(new Vec3(x, y, z), 128, 128, 128), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			if (another == null) {
				keepup = false;
			} else {
				if (another instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get())) {
					keepup = false;
				}
			}
			if (keepup) {
				entity.setShiftKeyDown(true);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 200, 0, false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 200, 1, false, false));
			}
		}
		if (entity instanceof TideBishopEntity) {
			keepup = true;
			another = (Entity) world.getEntitiesOfClass(TideDeathrepellerEntity.class, AABB.ofSize(new Vec3(x, y, z), 128, 128, 128), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			if (another == null) {
				keepup = false;
			} else {
				if (another instanceof LivingEntity _livEnt10 && _livEnt10.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get())) {
					keepup = false;
				}
			}
			if (keepup) {
				entity.setShiftKeyDown(true);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 400, 0, false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 400, 0, false, false));
			}
		}
		return keepup;
	}
}
