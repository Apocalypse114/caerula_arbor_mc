package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

public class EndermanTeleportProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double sx = 0;
		double sy = 0;
		double sz = 0;
		if (world.isClientSide()) {
			return;
		}
		if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) >= 6) {
			sx = sourceentity.getX();
			sy = sourceentity.getY();
			sz = sourceentity.getZ();
			if (IsValidPlaceProcedure.execute(world, sx, sy, sz)) {
				if (IsValidEnemyForSeabornProcedure.execute(sourceentity, entity) || (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == sourceentity) {
					TeleportToProcedure.execute(world, entity, x, y, z, sx, sy, sz);
					DeductPlayerSanityProcedure.execute(sourceentity,
							(entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity7.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 15);
					if (entity instanceof Mob _entity && sourceentity instanceof LivingEntity _ent)
						_entity.setTarget(_ent);
					sourceentity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), entity),
							(float) ((entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.75));
				}
			}
		}
	}
}
