package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

public class ArrowFollowProcedure {
	public static void execute(Entity entity, Entity immediatesourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		Entity enemy = null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(enemy == null)) {
			immediatesourceentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + enemy.getBbHeight() * 0.75), (enemy.getZ())));
		}
	}
}
