package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedPiglinEntity;
import net.apocalypse.careunite.entity.OceanizedBruteEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class PiglinFlyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double sklp = 0;
		if (entity.isAlive()) {
			sklp = entity instanceof OceanizedBruteEntity _datEntI ? _datEntI.getEntityData().get(OceanizedBruteEntity.DATA_skillp) : 0;
			if (sklp >= 0) {
				if (entity instanceof OceanizedBruteEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedBruteEntity.DATA_skillp, (int) (sklp - 1));
			} else {
				if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 5 && !(entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get()))) {
					if (entity instanceof OceanizedBruteEntity) {
						((OceanizedBruteEntity) entity).setAnimation("animation.oceanized_brute.skill");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 40, 1, false, false));
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ())));
					if (entity instanceof OceanizedBruteEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedBruteEntity.DATA_skillp, 5);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 80, 0, false, false));
					CaerulaArborMod.queueServerWork(20, () -> {
						if (entity.isAlive()) {
							BruteAttackProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					});
				}
			}
		}
		if (!(sourceentity instanceof OceanizedPiglinEntity)) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof OceanizedPiglinEntity) {
						if (entityiterator instanceof Mob _entity && sourceentity instanceof LivingEntity _ent)
							_entity.setTarget(_ent);
					}
				}
			}
		}
	}
}
