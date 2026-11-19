package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.Entity;

public class SummonDecayedEliteFishProcedure {
	public static void execute(LevelAccessor world, double xx, double yy, double zz) {
		Entity entity = SummonEliteFishProcedure.execute(world, xx, yy, zz);
		if (entity instanceof LivingEntity living && world.getRandom().nextFloat() < 0.33f){
			AttributeInstance inst = living.getAttribute(CaerulaArborModAttributes.SUMMONABLE.get());
			if (inst != null) inst.setBaseValue(0);
		}
	}
}
