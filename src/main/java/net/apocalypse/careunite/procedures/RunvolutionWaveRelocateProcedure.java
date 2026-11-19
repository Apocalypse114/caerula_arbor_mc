package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class RunvolutionWaveRelocateProcedure {
	public static void execute(double x, double z, Entity ent, double s) {
		if (ent == null)
			return;
		Vec2 delta = new Vec2((float) (ent.getX() - x), (float) (ent.getZ() - z));
		delta = delta.scale((float)s);
		ent.setDeltaMovement(Vec3.ZERO);
		ent.teleportTo(x + delta.x, ent.getY(), z + delta.y);
        if (ent instanceof ServerPlayer plr){
            plr.connection.teleport(x + delta.x, ent.getY(), z+delta.y, ent.getYRot(), ent.getXRot());
        }
	}
}
