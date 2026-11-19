package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.AegirMortarEntity;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

public class AegirMortarInitProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
			_livingEntity1.getAttribute(Attributes.MAX_HEALTH).setBaseValue(((double) CaerulaConfigsConfiguration.MORTAR_HEALTH.get()));
		if (entity instanceof LivingEntity _entity)
			_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
			_livingEntity5.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(((double) CaerulaConfigsConfiguration.MORTAR_ATTACK.get()));
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_deploy")), SoundSource.BLOCKS, 2, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_deploy")), SoundSource.BLOCKS, 2, 1, false);
			}
		}
		if (entity instanceof AegirMortarEntity) {
			((AegirMortarEntity) entity).setAnimation("animation.aegir_mortar.start");
		}
	}
}
