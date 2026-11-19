package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.OceanizedChickenEntity;

public class CreativeEggInteractProcedure {
	public static InteractionResult execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if (new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(sourceentity)) {
			if ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.NETHERSEA_CHICKEN_EGG.get()) : false) {
				if (entity instanceof OceanizedChickenEntity _datEntL2 && _datEntL2.getEntityData().get(OceanizedChickenEntity.DATA_IS_CHILD)) {
					if (entity instanceof OceanizedChickenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedChickenEntity.DATA_GROW_TIME, 1);
				} else {
					if (entity instanceof OceanizedChickenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedChickenEntity.DATA_LAY_COOLDOWN, 1);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
