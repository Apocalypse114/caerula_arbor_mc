package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;

public class GenNextActionProcedure {
	public static boolean mayDoAction0(Entity entity){
		if (entity instanceof LivingEntity living){
			AttributeInstance ins = living.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get());
			if (ins != null) return ins.getBaseValue() <= 1;
		}
		return false;
	}
    public static boolean mayDoAction1(Entity entity){
		return entity instanceof OceanizedWanderingTraderEntity trd && !trd.isGunmu();
	}
    public static boolean mayDoAction2(Entity entity){
    	return entity instanceof OceanizedWanderingTraderEntity trd && trd.lightLlama() != null;
    }
    public static boolean mayDoAction3(Entity entity){
    	return entity instanceof OceanizedWanderingTraderEntity trd && trd.canSummonLLama();
    }
    
	public static double execute(Entity entity, double currentActionId) {
		if (entity == null)
			return -1;
		List<Integer> candidate = new ArrayList<>();
       	if (currentActionId != 0 && mayDoAction0(entity)) candidate.add(0);
       	if (currentActionId != 1 && mayDoAction1(entity)) candidate.add(1);
       	if (currentActionId != 2 && mayDoAction2(entity)) candidate.add(2);
       	if (currentActionId != 3 && mayDoAction3(entity)) candidate.add(3);
       	if (candidate.isEmpty()) return -1;
       	return candidate.get(Mth.nextInt(entity.level().random, 0, candidate.size()-1));
	}
}
