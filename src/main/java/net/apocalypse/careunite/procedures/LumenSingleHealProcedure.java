package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class LumenSingleHealProcedure {
	public static void execute(LevelAccessor world, Entity obj, boolean square, double num) {
		if (obj == null)
			return;
		double tx = 0;
		double ty = 0;
		double tz = 0;
		if (obj.isAlive()) {
			tx = obj.getX();
			ty = obj.getY() + 0.5;
			tz = obj.getZ();
			LivingHeal1Procedure.execute(obj, num);
			RevivesPlayerSanityProcedure.execute(obj, num * 50);
			if (obj instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get(), 240, 1, false, false));
			if (obj instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_SANITY_RESISTANCE.get(), 240, 3, false, false));
			if (square) {
				{
					final Vec3 _center = new Vec3(tx, (ty - 0.5), tz);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator == obj) {
							continue;
						}
						if (CanInquisitionHealProcedure.execute(entityiterator)) {
							LivingHeal1Procedure.execute(entityiterator, num * 0.6);
							RevivesPlayerSanityProcedure.execute(entityiterator, num * 18);
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get(), 240, 1, false, false));
						}
					}
				}
				for (int index0 = 0; index0 < 9; index0++) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.LUMEN_ICON.get()), (tx + index0 - 4), ty, (tz + 4), 1, 0, 0, 0, 0.1);
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.LUMEN_ICON.get()), (tx + index0 - 4), ty, (tz - 4), 1, 0, 0, 0, 0.1);
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.LUMEN_ICON.get()), (tx - 4), ty, (tz + index0 - 4), 1, 0, 0, 0, 0.1);
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.LUMEN_ICON.get()), (tx + 4), ty, (tz + index0 - 4), 1, 0, 0, 0, 0.1);
				}
			} else {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.LUMEN_ICON.get()), tx, ty, tz, 6, 0.75, 0.75, 0.75, 0.25);
			}
		}
	}
}
