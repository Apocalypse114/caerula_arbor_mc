package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class NearestEnemyForIshmaelProcedure {
	public static void execute(LevelAccessor world, Entity center, Entity entity, double damage, double n) {
		if (center == null || entity == null)
			return;
		double minD = 0;
		double dis = 0;
		Entity eme = null;
		if (n > 0) {
			minD = 999;
			{
				final Vec3 _center = new Vec3((center.getX()), (center.getY()), (center.getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(24 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (IsValidEnemyForIshmaelProcedure.execute(entityiterator, entity)) {
						if (entityiterator instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get())) {
							continue;
						}
						if (entityiterator == center) {
							continue;
						}
						dis = entityiterator != null ? center.distanceTo(entityiterator) : -1;
						if (dis < minD) {
							minD = dis;
							eme = entityiterator;
						}
					}
				}
			}
			if (!(eme == null)) {
				eme.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ishmael_magic"))), entity, entity),
						(float) damage);
				if (Math.random() < 0.5) {
					if (eme instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.TRAPPED.get(), 60, 0, false, false));
				}
				if (eme instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 5, 0, false, false));
				LinkFlameToEntityProcedure.execute(world, center, eme);
				if (!eme.isAlive()) {
					NearestEnemyForIshmaelProcedure.execute(world, eme, entity, damage, n - 1);
				}
			}
		}
	}
}
