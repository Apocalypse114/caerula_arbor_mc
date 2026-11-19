package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;

import java.util.Comparator;

public class WanderingshowBoardProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity plr = null;
		if ((entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_PHASE) : 0) > 0) {
			if (entity instanceof OceanizedWanderingTraderEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceanizedWanderingTraderEntity.DATA_SHOW_BOARD, false);
		} else {
			if ((entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_TRADE_LEFT) : 0) <= 0) {
				if (entity instanceof OceanizedWanderingTraderEntity _datEntSetL)
					_datEntSetL.getEntityData().set(OceanizedWanderingTraderEntity.DATA_SHOW_BOARD, false);
				return;
			}
			plr = (Entity) world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 16, 16, 16), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			if (!(plr == null)) {
				if (entity instanceof OceanizedWanderingTraderEntity _datEntSetL)
					_datEntSetL.getEntityData().set(OceanizedWanderingTraderEntity.DATA_SHOW_BOARD, ((plr instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.REDSTONE_INGOT.get()));
			} else {
				if (entity instanceof OceanizedWanderingTraderEntity _datEntSetL)
					_datEntSetL.getEntityData().set(OceanizedWanderingTraderEntity.DATA_SHOW_BOARD, false);
			}
		}
	}
}
