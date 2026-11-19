package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Comparator;

public class ThirsterMapAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double num = 0;
		Entity enemy = null;
		num = GetLimbNumProcedure.execute(world, x, y, z, entity);
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (num > 0) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
						if (!(entityiterator == enemy)) {
							continue;
						}
					}
					if (!(entityiterator instanceof LivingEntity)) {
						continue;
					}
					if (entityiterator instanceof Player) {
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
						}.checkGamemode(entityiterator) || new Object() {
							public boolean checkGamemode(Entity _ent) {
								if (_ent instanceof ServerPlayer _serverPlayer) {
									return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
								} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
									return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
											&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
								}
								return false;
							}
						}.checkGamemode(entityiterator)) {
							continue;
						}
					}
					if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 20) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_real")))), (float) num);
						DeductPlayerSanityProcedure.execute(entityiterator, num * 25);
					}
				}
			}
		}
	}
}
