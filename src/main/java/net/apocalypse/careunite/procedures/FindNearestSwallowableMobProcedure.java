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

import net.apocalypse.careunite.entity.UmbrellaAbyssalEntity;
import net.apocalypse.careunite.entity.SplasherAbyssalEntity;
import net.apocalypse.careunite.entity.PredatorAbyssalEntity;
import net.apocalypse.careunite.entity.GuideAbyssalEntity;
import net.apocalypse.careunite.entity.CrackerAbyssalEntity;
import net.apocalypse.careunite.entity.BaselayerAbyssalEntity;

import java.util.List;
import java.util.Comparator;

public class FindNearestSwallowableMobProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return null;
		double minDIst = 0;
		double curDist = 0;
		double curPlayerDist = 0;
		double minPlayerDist = 0;
		double bestowed = 0;
		Entity sacrifice = null;
		Entity player = null;
		Entity aMaya = null;
		minDIst = 999;
		minPlayerDist = 999;
		{
			final Vec3 _center = new Vec3(x, (y + 24), z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:endspeaker_edible")))) {
					if (entityiterator instanceof BaselayerAbyssalEntity && InquirybilityProcedure.execute(world, 0)) {
						continue;
					} else if (entityiterator instanceof PredatorAbyssalEntity && InquirybilityProcedure.execute(world, 1)) {
						continue;
					} else if (entityiterator instanceof GuideAbyssalEntity && InquirybilityProcedure.execute(world, 2)) {
						continue;
					} else if (entityiterator instanceof SplasherAbyssalEntity && InquirybilityProcedure.execute(world, 3)) {
						continue;
					} else if (entityiterator instanceof UmbrellaAbyssalEntity && InquirybilityProcedure.execute(world, 4)) {
						continue;
					} else if (entityiterator instanceof CrackerAbyssalEntity && InquirybilityProcedure.execute(world, 5)) {
						continue;
					}
					curDist = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
					if (curDist < minDIst) {
						minDIst = curDist;
						sacrifice = entityiterator;
					}
				} else if (entityiterator instanceof Player && !(new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayer _serverPlayer) {
							return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
						} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
							return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
									&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
						}
						return false;
					}
				}.checkGamemode(entityiterator))) {
					curPlayerDist = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
					if (curPlayerDist < minPlayerDist) {
						minPlayerDist = curPlayerDist;
						player = entityiterator;
					}
				} else if (aMaya == null && ((entityiterator.getDisplayName().getString()).equals("\u963F\u739B\u96C5") || (entityiterator.getDisplayName().getString()).equals("Amaya"))) {
					if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring"))) || (entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity) {
						aMaya = entityiterator;
					}
				}
			}
		}
		if (sacrifice == null) {
			if (aMaya == null) {
				if (player == null) {
					return entity;
				}
				return player;
			}
			return aMaya;
		}
		if (sacrifice instanceof BaselayerAbyssalEntity) {
			bestowed = 0;
		} else if (sacrifice instanceof PredatorAbyssalEntity) {
			bestowed = 1;
		} else if (sacrifice instanceof GuideAbyssalEntity) {
			bestowed = 2;
		} else if (sacrifice instanceof SplasherAbyssalEntity) {
			bestowed = 3;
		} else if (sacrifice instanceof UmbrellaAbyssalEntity) {
			bestowed = 4;
		} else if (sacrifice instanceof CrackerAbyssalEntity) {
			bestowed = 5;
		}
		BestowAbilityProcedure.execute(world, bestowed);
		return sacrifice;
	}
}
