package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import java.util.List;
import java.util.ArrayList;

public class ChoseEchoNoReptProcedure {
	public static double execute(Entity entity){
		return (double) execute1(entity);
	}
	private static int execute1(Entity entity) {
		if (entity == null)
			return -1;

		double t = 0;
		List<Integer> candidate = new ArrayList<>(8);
        for(int i=0; i<8; i++){
        	t = GetEchoProcedure.execute(entity, i);
            if(t > -0.5 && t <= 0) candidate.add(i);
        }
        
        if(!candidate.isEmpty()){
            return candidate.get(Mth.nextInt(entity.level().getRandom(), 0 , candidate.size()-1));
        }
        return -1;
	}
}
