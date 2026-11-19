package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.ThirsterEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class ThirsterSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double barr = 0;
		double perc = 0;
		double sklp1 = 0;
		double d = 0;
		double maxH = 0;
		double angle = 0;
		double interg = 0;
		double dura = 0;
		if (world.isClientSide()) {
			return;
		}
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof ThirsterEntity _datEntI ? _datEntI.getEntityData().get(ThirsterEntity.DATA_SKILL_P) : 0;
		dura = entity instanceof ThirsterEntity _datEntI ? _datEntI.getEntityData().get(ThirsterEntity.DATA_DURATION) : 0;
		interg = entity instanceof ThirsterEntity _datEntI ? _datEntI.getEntityData().get(ThirsterEntity.DATA_INTEGRATION) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		barr = entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get())
				? _livingEntity6.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()).getBaseValue()
				: 0;
		if (dura > 0) {
			if (entity instanceof ThirsterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ThirsterEntity.DATA_DURATION, (int) (dura - 1));
			if (barr <= 0) {
				if (entity instanceof ThirsterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThirsterEntity.DATA_DURATION, 0);
				if (entity instanceof ThirsterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThirsterEntity.DATA_DIZZY_NUM, 2);
			}
		} else {
			if (barr > 0) {
				if (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()))
					_livingEntity10.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()).setBaseValue(0);
				ThirsterSanityAttackProcedure.execute(world, x, y, z, entity);
			}
		}
		if (sklp1 > 0) {
			if (entity instanceof ThirsterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ThirsterEntity.DATA_SKILL_P, (int) (sklp1 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if (entity instanceof ThirsterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThirsterEntity.DATA_SKILL_P, 600);
				if (entity instanceof ThirsterEntity) {
					((ThirsterEntity) entity).setAnimation("animation.thirster.skill");
				}
				CaerulaArborMod.queueServerWork(5, () -> {
					ThirsterSpitProcedure.execute(world, x, y, z, entity);
				});
			}
		}
		if (entity.tickCount % 20 == 10) {
			ThirsterMapAttackProcedure.execute(world, x, y, z, entity);
		}
	}
}
