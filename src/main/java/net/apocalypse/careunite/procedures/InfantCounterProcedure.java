package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.CorrectionalPhalanxyInfantryEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class InfantCounterProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof Player) {
			return;
		}
		if ((entity instanceof CorrectionalPhalanxyInfantryEntity _datEntI ? _datEntI.getEntityData().get(CorrectionalPhalanxyInfantryEntity.DATA_skillp1) : 0) <= 0) {
			if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 5 && entity.isAlive()) {
				if (entity instanceof CorrectionalPhalanxyInfantryEntity _datEntSetI)
					_datEntSetI.getEntityData().set(CorrectionalPhalanxyInfantryEntity.DATA_skillp1, 100);
				if (entity instanceof CorrectionalPhalanxyInfantryEntity) {
					((CorrectionalPhalanxyInfantryEntity) entity).setAnimation("animation.correctional_phalanx _infantry.heavyattack");
				}
				CaerulaArborMod.queueServerWork(20, () -> {
					if (!(sourceentity == null)) {
						sourceentity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack")))),
								(float) ((entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity7.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 2));
						sourceentity.push((entity.getLookAngle().x * 0.64), 0, (entity.getLookAngle().z * 0.64));
					}
				});
			}
		}
	}
}
