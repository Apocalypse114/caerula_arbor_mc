package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class OnEchoPlayerKillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double perc = 0;
		double evo = 0;
		if (entity instanceof Monster || (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == sourceentity) {
			if (GetEchoVissiProcedure.execute(sourceentity) != 0) {
				evo = CaerulaArborModVariables.MapVariables.get(world).evo_point_subsisting;
				if (evo >= 3) {
					perc = 0.15;
				} else {
					perc = 0.1;
				}
				if (Math.random() < perc) {
					GiveEchoRewardProcedure.execute(world, x, y, z, entity);
					GiveEchoRewardProcedure.execute(world, x, y, z, entity);
					ChangePlayerLPOrShieldProcedure.execute(world, sourceentity, true, -1, "echo_vicissis");
					if (evo >= 3 && HaveShieldProcedure.execute(sourceentity)) {
						ChangePlayerLPOrShieldProcedure.execute(world, sourceentity, false, -1, "echo_vicissis");
					}
				}
			}
		}
	}
}
