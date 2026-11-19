package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

public class GetNearestEnemyProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity exception0, Entity exception1, Entity obj) {
		if (exception0 == null || exception1 == null || obj == null)
			return null;
		Entity enemy = null;
		double minDist = 0;
		double d = 0;
		minDist = 999;
		for (Entity entityiterator : world.getEntities(obj, new AABB((x + 42), (y + 40), (z + 42), (x - 42), (y - 40), (z - 42)))) {
			if (!(entityiterator instanceof LivingEntity)) {
				continue;
			}
			if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				continue;
			}
			d = entityiterator != null ? obj.distanceTo(entityiterator) : -1;
			if (d <= 42) {
				if (entityiterator == exception0) {
					continue;
				}
				if (entityiterator == exception1) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:sea_friend")))) {
					continue;
				}
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
				}.checkGamemode(entityiterator)) {
					continue;
				}
				if (d < minDist) {
					minDist = d;
					enemy = entityiterator;
				}
			}
		}
		return enemy;
	}
}
