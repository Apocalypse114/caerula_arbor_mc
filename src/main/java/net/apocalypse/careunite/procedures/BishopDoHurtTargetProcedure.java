package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.BishopFishEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class BishopDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double amplifi = 0;
		if (sourceentity instanceof BishopFishEntity _datEntSetI)
			_datEntSetI.getEntityData().set(BishopFishEntity.DATA_duration, (int) ((sourceentity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_duration) : 0) + 40));
		CaerulaArborMod.queueServerWork(12, () -> {
			for (int index0 = 0; index0 < 120; index0++) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.SMOKE, (sourceentity.getX() + 12 * Math.sin(Math.toRadians(3 * index0))), (sourceentity.getY() + 0.15), (sourceentity.getZ() + 12 * Math.cos(Math.toRadians(3 * index0))), 16, 0.15, 0.15, 0.15,
							0.1);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bishopfish_attack")), SoundSource.HOSTILE, (float) 2.5, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bishopfish_attack")), SoundSource.HOSTILE, (float) 2.5, 1, false);
				}
			}
			for (Entity entityiterator : world.getEntities(sourceentity,
					new AABB((sourceentity.getX() + 12), (sourceentity.getY() - 4), (sourceentity.getZ() + 12), (sourceentity.getX() - 12), (sourceentity.getY() + 6), (sourceentity.getZ() - 12)))) {
				if (entityiterator instanceof LivingEntity) {
					if ((entityiterator != null ? sourceentity.distanceTo(entityiterator) : -1) <= 13 && IsValidEnemyForSeabornProcedure.execute(entityiterator, sourceentity)) {
						entityiterator.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			}
		});
	}
}
