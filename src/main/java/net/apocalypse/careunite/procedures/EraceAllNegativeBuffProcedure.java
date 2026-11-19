package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class EraceAllNegativeBuffProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		boolean flag = false;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
{
			List<MobEffectInstance> effects = new ArrayList<>(_entity.getActiveEffects());
			for (MobEffectInstance effect: effects){
				if (effect.getAmplifier() > 7) continue;
	            if(!effect.isInfiniteDuration() && effect.getEffect().getCategory() == MobEffectCategory.HARMFUL){
	                _entity.removeEffect(effect.getEffect());
	                flag = true;
	            }
			}
			return flag;
		}
		return flag;
	}
}
