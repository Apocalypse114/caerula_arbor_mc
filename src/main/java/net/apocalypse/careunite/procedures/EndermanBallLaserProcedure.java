package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.List;
import java.util.Comparator;

public class EndermanBallLaserProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double dist) {
		if (entity == null)
			return;
		double tgtX = 0;
		double tgtY = 0;
		double tgtZ = 0;
		double ctX = 0;
		double ctZ = 0;
		ctX = x + entity.getLookAngle().x * dist;
		ctZ = z + entity.getLookAngle().z * dist;
		{
			final Vec3 _center = new Vec3(ctX, y, ctZ);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				tgtX = entityiterator.getX();
				tgtY = entityiterator.getY();
				tgtZ = entityiterator.getZ();
				if (new Vec3(ctX, y, ctZ).distanceTo(new Vec3(tgtX, tgtY, tgtZ)) <= 3) {
					if (!(entityiterator instanceof Mob)) {
						continue;
					}
					if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity)) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), entity),
								(float) ((entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity7.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.9));
						if (ModGriefSettingsProcedure.execute(world)) {
							if ((world.getBlockState(BlockPos.containing(tgtX, tgtY, tgtZ))).canBeReplaced() && CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(tgtX, tgtY, tgtZ))) {
								world.setBlock(BlockPos.containing(tgtX, tgtY, tgtZ), CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState(), 3);
							}
						}
					}
				}
			}
		}
	}
}
