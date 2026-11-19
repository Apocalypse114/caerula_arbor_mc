package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import java.util.List;
import java.util.ArrayList;


import net.apocalypse.careunite.CaerulaArborMod;

public class RemoveRandomOnRejectionProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;

		List<Integer> candidate = new ArrayList<>(4);
        for(int i=0; i<=3; i++){
            if(IsRejectionOnProcedure.execute(entity, i)) candidate.add(i);
        }
        
        if(!candidate.isEmpty()){
            int ind = Mth.nextInt(entity.level().getRandom(), 0 , candidate.size()-1);
            SetRejectionOffProcedure.execute(entity, candidate.get(ind));
        }
	}
}
