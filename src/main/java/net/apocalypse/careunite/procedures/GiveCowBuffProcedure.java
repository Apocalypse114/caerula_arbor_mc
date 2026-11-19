package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedCowEntity;

public class GiveCowBuffProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double milk = 0;
		if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.MUTE.get())) && entity instanceof OceanizedCowEntity _datEntL1 && _datEntL1.getEntityData().get(OceanizedCowEntity.DATA_skill)
				&& ModGriefSettingsProcedure.execute(world)) {
			if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.COW_BUFF.get()))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COW_BUFF.get(), 20, 0, false, false));
			}
		}
		milk = entity instanceof OceanizedCowEntity _datEntI ? _datEntI.getEntityData().get(OceanizedCowEntity.DATA_milk_cooldown) : 0;
		if (milk > 0) {
			if (entity instanceof OceanizedCowEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedCowEntity.DATA_milk_cooldown, (int) (milk - 1));
		}
	}
}
