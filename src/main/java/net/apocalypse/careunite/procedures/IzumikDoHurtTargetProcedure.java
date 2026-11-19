package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.IzumikEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class IzumikDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		CaerulaArborMod.queueServerWork(16, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:izumik_attack")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.85, 0.15));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:izumik_attack")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.85, 0.15), false);
				}
			}
			if (sourceentity.isAlive()) {
				if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 13) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:izumik_normal_attack"))), sourceentity),
							(float) (sourceentity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					if ((sourceentity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0) >= 1) {
						if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 4) {
							entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), sourceentity),
									(float) ((sourceentity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity8.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
											* 1.5));
						} else {
							entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), sourceentity),
									(float) (sourceentity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity11.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
						}
						if ((sourceentity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0) >= 2 && Math.random() < 0.15) {
							if (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
								_livingEntity16.getAttribute(CaerulaArborModAttributes.NUMB.get())
										.setBaseValue(((entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get())
												? _livingEntity15.getAttribute(CaerulaArborModAttributes.NUMB.get()).getBaseValue()
												: 0) + 1));
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.FIREWORK, x, (y + 0.75), z, 16, 0.75, 0.75, 0.75, 0.1);
						}
					}
				}
			}
		});
	}
}
