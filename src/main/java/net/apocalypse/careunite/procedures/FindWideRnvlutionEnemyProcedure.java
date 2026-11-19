package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Comparator;

public class FindWideRnvlutionEnemyProcedure {
	public static Entity execute(LevelAccessor world, Entity entity, Entity exception0, Entity exception1, Entity obj) {
		if (entity == null || exception0 == null || exception1 == null || obj == null)
			return null;
		Entity enemy = null;
		double d = 0;
		double minDist = 0;
		{
			final Vec3 _center = new Vec3((obj.getX()), (obj.getY()), (obj.getZ()));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity)) {
						continue;
					}
				}
				d = entityiterator != null ? obj.distanceTo(entityiterator) : -1;
				if (d <= 24) {
					if (entityiterator == obj) {
						continue;
					}
					if (entityiterator == exception0) {
						continue;
					}
					if (entityiterator == exception1) {
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
					enemy = entityiterator;
				}
			}
		}
		return enemy;
	}
}
