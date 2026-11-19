package net.apocalypse.careunite.procedures;

import org.checkerframework.checker.units.qual.A;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.CaerulaArborMod;

public class ShootWitherSkullProcedure {
	public static void execute(LevelAccessor world, Entity from, double a, double dx, double dy, double dz, double inaccu, double speed, double xx, double yy, double zz) {
		if (from == null)
			return;
		double ddx = 0;
		double ddy = 0;
		double ddz = 0;
		double module = 0;
		double A = 0;
		module = Math.sqrt(dx * dx + dy * dy + dz * dz);
		if (module > 0) {
			ddx = (dx / module) * a;
			ddy = (dy / module) * a;
			ddz = (dz / module) * a;
		}
		CaerulaArborMod.queueServerWork(Mth.nextInt(RandomSource.create(), 0, 4), () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(xx, yy, zz), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")), SoundSource.HOSTILE, (float) 0.85, 1);
				} else {
					_level.playLocalSound(xx, yy, zz, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")), SoundSource.HOSTILE, (float) 0.85, 1, false);
				}
			}
		});
		if (world instanceof ServerLevel projectileLevel) {
			Projectile _entityToSpawn = new Object() {
				public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
					AbstractHurtingProjectile entityToSpawn = new WitherSkull(EntityType.WITHER_SKULL, level);
					entityToSpawn.setOwner(shooter);
					entityToSpawn.xPower = ax;
					entityToSpawn.yPower = ay;
					entityToSpawn.zPower = az;
					return entityToSpawn;
				}
			}.getFireball(projectileLevel, from, ddx, ddy, ddz);
			_entityToSpawn.setPos(xx, yy, zz);
			_entityToSpawn.shoot(dx, dy, dz, (float) speed, (float) inaccu);
			projectileLevel.addFreshEntity(_entityToSpawn);
		}
	}
}
