package net.apocalypse.careunite.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.CaerulaArborMod;
import net.apocalypse.careunite.entity.IzumikEntity;

import net.apocalypse.careunite.EntitySpawnHelper;

import java.util.List;
import java.util.Comparator;

public class IzumikChangeProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        boolean success = false;
        Entity owner = null;
        if(entity.tickCount % 5 != 0) return;
		owner = (Entity) world.getEntitiesOfClass(IzumikEntity.class, AABB.ofSize(new Vec3(x, y, z), 85, 32, 85), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(owner == null)) {
			if ((entity instanceof Mob _entity) && (owner.getEntityData().get(IzumikEntity.DATA_phase) == 0))
				_entity.getNavigation().moveTo((owner.getX()), (owner.getY()+4), (owner.getZ()), 0.75);
		} else {
			if (entity instanceof Mob _entity)
				_entity.getNavigation().stop();
		}

        {
            final Vec3 _center = new Vec3(x, y, z);
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                if (entityiterator == entity) {
                    continue;
                }
                if (!(entityiterator instanceof Mob)) {
					if (!(entityiterator instanceof Player)) {
						continue;
					} else {
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
					}
				}
                if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
                    continue;
                }
                if (entity.distanceTo(entityiterator) <= 2) {
                    if (!world.isClientSide()) {
                        if (world instanceof ServerLevel serverLevel) {
                            success = EntitySpawnHelper.spawnRandomEntityFromTag(entity, serverLevel, x, y, z);
                        }
                    }
                    if (success) {
                        
                        if (world instanceof ServerLevel _level)
                            _level.sendParticles(ParticleTypes.CLOUD, x, (y + 0.75), z, 32, 0.75, 0.75, 0.75, 0.1);
                        if (!entity.level().isClientSide())
                            entity.discard();
                        break;
                    }
                }
            }
        }
    }
}