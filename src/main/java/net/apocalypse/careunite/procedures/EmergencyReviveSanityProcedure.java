package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import java.util.List;
import java.util.Comparator;

public class EmergencyReviveSanityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		double tx = 0;
		double tz = 0;
		double dist = 0;
		if (blockstate.getBlock().getStateDefinition().getProperty("powered") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1)) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (!(entityiterator instanceof LivingEntity)) {
						continue;
					}
					if (new Vec3(x, y, z).distanceTo(new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()))) > 16) {
						continue;
					}
					if (entityiterator instanceof Player) {
						RevivesPlayerSanityProcedure.execute(entityiterator, 20);
					} else {
						RevivesPlayerSanityProcedure.execute(entityiterator, 10);
					}
				}
			}
			for (int index0 = 0; index0 < 120; index0++) {
				dist = Mth.nextDouble(RandomSource.create(), 13, 16);
				tx = x + 0.5 + dist * Math.cos(Math.toRadians(index0 * 3));
				tz = z + 0.5 + dist * Math.sin(Math.toRadians(index0 * 3));
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.GLOW, tx, (y + 0.25), tz, 2, 0.1, 0.1, 0.1, 0);
			}
		}
	}
}
