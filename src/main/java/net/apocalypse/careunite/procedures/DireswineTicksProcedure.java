package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TiderageDireswineEntity;

public class DireswineTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double bonus = 0;
		Entity tgt = null;
		bonus = entity instanceof TiderageDireswineEntity _datEntI ? _datEntI.getEntityData().get(TiderageDireswineEntity.DATA_RUN_BONUS) : 0;
		tgt = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(tgt == null) && tgt.isAlive() && GetSpeedProcedure.execute(entity) > 0.1) {
			if (bonus < 8 && entity.tickCount % 10 == 5) {
				if (!world.isClientSide()
						&& (entity instanceof LivingEntity _livEnt6 && _livEnt6.hasEffect(CaerulaArborModMobEffects.DIZZY.get()) || entity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(CaerulaArborModMobEffects.FROZEN.get())
								|| entity instanceof LivingEntity _livEnt8 && _livEnt8.hasEffect(CaerulaArborModMobEffects.TRAPPED.get()) || entity instanceof LivingEntity _livEnt9 && _livEnt9.hasEffect(MobEffects.SLOW_FALLING)
								|| (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? _livEnt.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() : 0) >= 2)) {
					if (entity instanceof TiderageDireswineEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TiderageDireswineEntity.DATA_RUN_BONUS, 0);
				} else {
					if (entity instanceof TiderageDireswineEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TiderageDireswineEntity.DATA_RUN_BONUS, (int) (bonus + 1));
				}
			}
			if (entity.tickCount % 2 == 1) {
				DireswineDashProcedure.execute(world, x, y, z, entity, 2.5, 1 + 0.15 * bonus);
			}
		}
	}
}
