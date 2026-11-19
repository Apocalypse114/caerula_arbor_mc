package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.registries.Registries;

public class DoLaserRangedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double atk = 0;
		atk = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		atk = atk * (1 + 0.3 * Math.max(GetBaseHeldProcedure.execute(entity, false, 0), 0));
		for (Entity entityiterator : world.getEntities(entity, new AABB((x + 2.4), (y + 3), (z + 2.4), (x - 2.4), (y - 8), (z - 2.4)))) {
			if (IsValidEnemyForAlchemyProcedure.execute(entityiterator, entity)) {
				entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), entity), (float) atk);
			}
		}
	}
}
