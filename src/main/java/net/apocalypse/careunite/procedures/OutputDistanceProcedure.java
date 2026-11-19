package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.ChitinGolemEntity;

public class OutputDistanceProcedure {
	public static InteractionResult execute(double x, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		boolean root = false;
		double rx = 0;
		double rz = 0;
		double dist = 0;
		if ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.RULER.get()) : false) {
			rx = x - (entity instanceof ChitinGolemEntity _datEntI ? _datEntI.getEntityData().get(ChitinGolemEntity.DATA_rootX) : 0);
			rz = z - (entity instanceof ChitinGolemEntity _datEntI ? _datEntI.getEntityData().get(ChitinGolemEntity.DATA_rootZ) : 0);
			if (sourceentity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(("" + Math.round(Math.pow(10, 3) * new Vec3(0, 0, 0).distanceTo(new Vec3(rx, 0, rz))) / Math.pow(10, 3))), true);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
