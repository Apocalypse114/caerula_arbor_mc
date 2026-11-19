package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.entity.JuniorWarriorPriestEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class WarriorShieldHitProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double dist = 0;
		if (entity.isAlive() && sourceentity.isAlive()) {
			if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 2.4 && (entity instanceof JuniorWarriorPriestEntity _datEntI ? _datEntI.getEntityData().get(JuniorWarriorPriestEntity.DATA_skill_p) : 0) <= 0) {
				if (entity instanceof JuniorWarriorPriestEntity) {
					((JuniorWarriorPriestEntity) entity).setAnimation("animation.warriorpriest.shieldattack");
				}
				if (entity instanceof JuniorWarriorPriestEntity _datEntSetI)
					_datEntSetI.getEntityData().set(JuniorWarriorPriestEntity.DATA_skill_p, 200);
				CaerulaArborMod.queueServerWork(15, () -> {
					if (sourceentity.isAlive()) {
						entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ())));
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("intentionally_empty")), SoundSource.HOSTILE, (float) 2.5, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("intentionally_empty")), SoundSource.HOSTILE, (float) 2.5, 1, false);
							}
						}
						sourceentity.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), entity),
								(float) ((entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity12.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1.5));
						sourceentity.push(0, 0.25, 0);
					}
				});
			}
		}
	}
}
