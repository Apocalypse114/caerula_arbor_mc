package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.ChemistWashbasinEntity;

public class BasinTickProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double atk = 0;
		double base = 0;
		double dura = 0;
		if (!world.isClientSide()) {
			base = GetBaseHeldProcedure.execute(entity, false, 0);
			if (entity instanceof ChemistWashbasinEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ChemistWashbasinEntity.DATA_BASE_HELD, (int) base);
		}
		if (entity.isAlive()) {
			if (base > 0) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.STRENGTH_OF_CROWD.get(), 5, (int) (base - 1), false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 5, (int) (base * 2 - 1), false, false));
			}
			atk = entity instanceof ChemistWashbasinEntity _datEntI ? _datEntI.getEntityData().get(ChemistWashbasinEntity.DATA_ATTACK_ANIM) : 0;
			if (atk > 0) {
				if (entity instanceof ChemistWashbasinEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ChemistWashbasinEntity.DATA_ATTACK_ANIM, (int) (atk - 1));
			}
			dura = entity instanceof ChemistWashbasinEntity _datEntI ? _datEntI.getEntityData().get(ChemistWashbasinEntity.DATA_DURA) : 0;
			if (dura > 0) {
				if (entity instanceof ChemistWashbasinEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ChemistWashbasinEntity.DATA_DURA, (int) (dura - 1));
			}
		}
	}
}
