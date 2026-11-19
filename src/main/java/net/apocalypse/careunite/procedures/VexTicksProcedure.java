package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.entity.OceanizedVexEntity;

import java.util.UUID;

public class VexTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		String uuid = "";
		if (entity.isAlive()) {
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			sklp1 = entity instanceof OceanizedVexEntity _datEntI ? _datEntI.getEntityData().get(OceanizedVexEntity.DATA_leftSurvivalTick) : 0;
			if (sklp1 <= 0) {
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.STARVE)), (float) Math.max(0.075 * (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1), 1));
			} else {
				if (entity instanceof OceanizedVexEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedVexEntity.DATA_leftSurvivalTick, (int) (sklp1 - 1));
			}
			if (entity instanceof Mob _mobEnt7 && _mobEnt7.isAggressive()) {
				if (entity instanceof OceanizedVexEntity animatable)
					animatable.setTexture("oceanized_vex_charging");
			} else {
				if (entity instanceof OceanizedVexEntity animatable)
					animatable.setTexture("oceanized_vex");
			}
		} else {
			uuid = entity instanceof OceanizedVexEntity _datEntS ? _datEntS.getEntityData().get(OceanizedVexEntity.DATA_SAYER) : "";
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
			if (!(enemy == null) && enemy.isAlive()) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
				VexChargeToSlayerProcedure.execute(entity, enemy);
			}
		}
		if (entity.tickCount % 40 == 15) {
			if (entity.getY() <= -64) {
				entity.push(0, 0.64, 0);
			} else if (DistFromGroundProcedure.execute(world, x, y, z)) {
				entity.push(0, (-0.64), 0);
			}
		}
	}
}
