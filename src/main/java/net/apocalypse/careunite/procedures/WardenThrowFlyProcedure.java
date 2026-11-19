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

public class WardenThrowFlyProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (entity.isAlive() && !(enemy == null)) {
			if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 4) {
				enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:warden_attack"))), entity),
						(float) ((entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1.5));
			}
			enemy.push(0, 1.25, 0);
		}
	}
}
