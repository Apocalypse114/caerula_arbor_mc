package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.SkadiEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class SkadiTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double rlx = 0;
		double sklp = 0;
		double sklp2 = 0;
		if (entity.isAlive()) {
			if (Math.random() < 0.001) {
				rlx = entity instanceof SkadiEntity _datEntI ? _datEntI.getEntityData().get(SkadiEntity.DATA_relax_cooldown) : 0;
				if (rlx <= 0) {
					if (!(entity instanceof Mob _mobEnt2 && _mobEnt2.isAggressive())) {
						if (entity instanceof Mob _entity)
							_entity.getNavigation().stop();
						if (entity instanceof SkadiEntity) {
							((SkadiEntity) entity).setAnimation("animation.skadi.relax");
						}
						rlx = 320;
					}
				}
			}
			if (rlx > 0) {
				rlx = rlx - 1;
			}
			sklp = entity instanceof SkadiEntity _datEntI ? _datEntI.getEntityData().get(SkadiEntity.DATA_skillp) : 0;
			sklp2 = entity instanceof SkadiEntity _datEntI ? _datEntI.getEntityData().get(SkadiEntity.DATA_skillp2) : 0;
			if (sklp <= 0) {
				if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
					if (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) != null ? entity.distanceTo((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)) : -1) < 3) {
						if (entity instanceof SkadiEntity) {
							((SkadiEntity) entity).setAnimation("animation.skadi.spin");
						}
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 40, 0, false, false));
						CaerulaArborMod.queueServerWork(10, () -> {
							SkadiSpinProcedure.execute(world, x, y, z, entity, 1.5);
						});
						CaerulaArborMod.queueServerWork(14, () -> {
							SkadiSpinProcedure.execute(world, x, y, z, entity, 2);
						});
						CaerulaArborMod.queueServerWork(20, () -> {
							SkadiSpinProcedure.execute(world, x, y, z, entity, 1.5);
						});
						if (IsSpecterAroundProcedure.execute(world, x, y, z)) {
							sklp = 170;
						} else {
							sklp = 200;
						}
					}
				}
			} else {
				sklp = sklp - 1;
			}
			if (sklp2 > 0) {
				if (entity instanceof SkadiEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiEntity.DATA_skillp2, (int) (sklp2 - 1));
			}
			if (entity instanceof SkadiEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SkadiEntity.DATA_relax_cooldown, (int) rlx);
			if (entity instanceof SkadiEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SkadiEntity.DATA_skillp, (int) sklp);
			HealFromGladiiaProcedure.execute(world, x, y, z, entity);
			SkadiGiveInspirationProcedure.execute(world, x, y, z, entity);
		}
	}
}
