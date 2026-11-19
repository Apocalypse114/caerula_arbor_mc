package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class OnGoldenChaliseTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double coin = 0;
		int lvl = 0;
		int lvlGreat = 0;
		coin = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
			.orElseGet(CaerulaArborModVariables.PlayerVariables::new)).plauyer_balance;
		if (coin > 0) {
			lvl = (int) (coin / 4);
			if (lvl > 400) lvl = 400;
			lvlGreat = lvl / 100;
			lvl = lvl % 100;
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
				if (lvlGreat > 0)
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.CHALISE_GREAT_BOOST.get(), 20, lvlGreat + 1, false, false));
				if (lvl > 0) 
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.CHALISE_BOOST.get(), 20, lvl + 1, false, false));
			}
		}
	}
}
