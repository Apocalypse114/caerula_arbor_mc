package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.entity.MoistDragonBreathEntity;

import java.util.UUID;

public class DragonBreathPurchaseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp1 = 0;
		String uuid = "";
		String uuid1 = "";
		Entity enemy = null;
		Entity owner = null;
		if (entity.tickCount >= 1200) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
		if (entity.isAlive()) {
			entity.clearFire();
			entity.setAirSupply(20);
			uuid = entity instanceof MoistDragonBreathEntity _datEntS ? _datEntS.getEntityData().get(MoistDragonBreathEntity.DATA_TARGET) : "";
			uuid1 = entity instanceof MoistDragonBreathEntity _datEntS ? _datEntS.getEntityData().get(MoistDragonBreathEntity.DATA_OWNER) : "";
			enemy = new Object() {
				Entity entityFromStringUUID(String uuid, Level world) {
					Entity _uuidentity = null;
					if (world instanceof ServerLevel _server) {
						try {
							_uuidentity = _server.getEntity(UUID.fromString(uuid));
						} catch (Exception e) {
						}
					}
					return _uuidentity;
				}
			}.entityFromStringUUID(uuid, (Level) world);
			owner = new Object() {
				Entity entityFromStringUUID(String uuid, Level world) {
					Entity _uuidentity = null;
					if (world instanceof ServerLevel _server) {
						try {
							_uuidentity = _server.getEntity(UUID.fromString(uuid));
						} catch (Exception e) {
						}
					}
					return _uuidentity;
				}
			}.entityFromStringUUID(uuid1, (Level) world);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.EDERMAN_PTC.get()), x, (y + 0.25), z, 3, 0.1, 0.1, 0.1, 0.1);
			if (!(enemy == null) && enemy.isAlive()) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + 0.5), (enemy.getZ())));
				if (entity.tickCount > 20) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) > 0.5) {
						DragonBreathChatgeProcedure.execute(entity, enemy);
					} else {
						DragonBreathExplodeProcedure.execute(world, x, y, z, entity);
					}
				}
			} else {
				enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
				if (!(enemy == null) && enemy.isAlive()) {
					if (entity instanceof MoistDragonBreathEntity _datEntSetS)
						_datEntSetS.getEntityData().set(MoistDragonBreathEntity.DATA_TARGET, (enemy.getStringUUID()));
				} else {
					DragonBreathExplodeProcedure.execute(world, x, y, z, entity);
				}
			}
		}
	}
}
