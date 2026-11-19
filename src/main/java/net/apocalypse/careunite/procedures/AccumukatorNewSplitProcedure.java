package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.AccumulatorProkaryoteEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class AccumukatorNewSplitProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity.isAlive() && entity.tickCount % 10 == 0) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5 && entity instanceof AccumulatorProkaryoteEntity _datEntL4
					&& _datEntL4.getEntityData().get(AccumulatorProkaryoteEntity.DATA_split)) {
				if (entity instanceof AccumulatorProkaryoteEntity) {
					((AccumulatorProkaryoteEntity) entity).setAnimation("animation.accumulator.split");
				}
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5));
				if (entity instanceof AccumulatorProkaryoteEntity _datEntSetL)
					_datEntSetL.getEntityData().set(AccumulatorProkaryoteEntity.DATA_split, false);
				if (!(entity instanceof LivingEntity _livEnt9 && _livEnt9.hasEffect(CaerulaArborModMobEffects.MUTE.get()))) {
					CaerulaArborMod.queueServerWork(10, () -> {
						SummonHoarderCloneProcedure.execute(world, entity);
					});
				}
			}
		}
	}
}
