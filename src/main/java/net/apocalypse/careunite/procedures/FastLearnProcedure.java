package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.entity.IzumikEntity;

public class FastLearnProcedure {
	public static InteractionResult execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if ((entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0) == 0) {
			if (new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(sourceentity)) {
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("izumik.fastlearn").getString())), false);
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("izumik.saying").getString())), false);
				if (entity instanceof IzumikEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IzumikEntity.DATA_growth_p, 20);
			}
		}
		return InteractionResult.PASS;
	}
}
