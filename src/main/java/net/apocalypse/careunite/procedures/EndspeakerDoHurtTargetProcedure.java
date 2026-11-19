package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

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

import net.apocalypse.careunite.entity.Endspeaker3Entity;
import net.apocalypse.careunite.entity.Endspeaker2Entity;
import net.apocalypse.careunite.CaerulaArborMod;

public class EndspeakerDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_attack_pre")), SoundSource.HOSTILE, (float) 2.5, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_attack_pre")), SoundSource.HOSTILE, (float) 2.5, 1, false);
			}
		}
		if (sourceentity instanceof Endspeaker3Entity) {
			CaerulaArborMod.queueServerWork(14, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 5.5) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_attack_hit")), SoundSource.HOSTILE, 3, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_attack_hit")), SoundSource.HOSTILE, 3, 1, false);
							}
						}
						entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:endspeaker_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity5.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			if (sourceentity instanceof Endspeaker3Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker3Entity.DATA_duration, (int) ((entity instanceof Endspeaker3Entity _datEntI ? _datEntI.getEntityData().get(Endspeaker3Entity.DATA_duration) : 0) + 30));
		} else if (sourceentity instanceof Endspeaker2Entity) {
			CaerulaArborMod.queueServerWork(13, () -> {
				if (sourceentity.isAlive()) {
					if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 5) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_attack_hit")), SoundSource.HOSTILE, 3, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_attack_hit")), SoundSource.HOSTILE, 3, 1, false);
							}
						}
						entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:endspeaker_attack"))), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			});
			if (sourceentity instanceof Endspeaker2Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker2Entity.DATA_duration, 25);
		}
	}
}
