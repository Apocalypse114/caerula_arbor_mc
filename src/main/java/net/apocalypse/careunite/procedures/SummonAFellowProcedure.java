package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class SummonAFellowProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		EntityType<?> type = entity.getType();
        if (entity.level() instanceof ServerLevel s){
            Entity e = type.spawn(s, entity.blockPosition(), MobSpawnType.MOB_SUMMONED);
            if (e instanceof LivingEntity living && s.getRandom().nextFloat() < 0.33f){
				AttributeInstance inst = living.getAttribute(CaerulaArborModAttributes.SUMMONABLE.get());
				if (inst != null) inst.setBaseValue(0);
			}
        }
	}
}
