package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.registries.Registries;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;

import net.minecraft.world.effect.MobEffectInstance;

public class DoCenserRangedProcedure {
	public static void timedLoop(int timedloopiterator, int timedlooptotal, int ticks, double x, double y, double z, LevelAccessor world) {
		CenserRimedParticleProcedure.execute(world, x, y, z, (timedloopiterator + 1) * 3.6);
		final int tick2 = ticks;
		CaerulaArborMod.queueServerWork(tick2, () -> {
			if (timedlooptotal > timedloopiterator + 1) {
				timedLoop(timedloopiterator + 1, timedlooptotal, tick2, x, y, z, world);
			}
		});
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amount) {
		if (entity == null)
			return;
		timedLoop(0, 5, 1, x, y + 1, z, world);
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(36 / 2d), 
				e -> IsValidEnemyForAlchemyProcedure.execute(e, entity));
			if (_entfound.isEmpty()) return;
			
int num = _entfound.size()/2;
			if (num > 1) amount /= num;
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator == entity) {
					continue;
				}
				if (entity.distanceTo(entityiterator) <= 18) {
					entityiterator.invulnerableTime = 0;
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 5, 8, false, false));
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), entity), (float) amount);
				}
			}
			if (entity instanceof LivingEntity ll) ll.heal((float)(amount * 0.25));
		}
	}
}
