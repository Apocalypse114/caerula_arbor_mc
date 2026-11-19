package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.ApostleProkaryoteEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class ApostleGetShelledProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean found = false;
		double perc = 0;
		if (entity.isAlive()) {
			if (!(entity instanceof ApostleProkaryoteEntity _datEntL1 && _datEntL1.getEntityData().get(ApostleProkaryoteEntity.DATA_shelled))) {
				for (int dx = (int) (-2); dx <= (int) 2; dx++) {
					for (int dy = (int) (-2); dy <= (int) 3; dy++) {
						for (int dz = (int) (-2); dz <= (int) 2; dz++) {
							if ((world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz))).getBlock() == CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get()) {
								entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((x + dx), (y + dy), (z + dz)));
								world.destroyBlock(BlockPos.containing(x + dx, y + dy, z + dz), false);
								found = true;
								break;
							}
						}
						if (found) {
							break;
						}
					}
					if (found) {
						break;
					}
				}
				if (found) {
					perc = (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
					if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting >= 4) {
						if (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
							_livingEntity12.getAttribute(Attributes.MAX_HEALTH).setBaseValue(
									((entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity11.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 2.2));
						if (entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
							_livingEntity14.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
									((entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity13.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.8));
					} else {
						if (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
							_livingEntity16.getAttribute(Attributes.MAX_HEALTH).setBaseValue(
									((entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity15.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 1.6));
						if (entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
							_livingEntity18.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
									((entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity17.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.4));
					}
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * perc));
					if (entity instanceof ApostleProkaryoteEntity _datEntSetL)
						_datEntSetL.getEntityData().set(ApostleProkaryoteEntity.DATA_shelled, true);
					if (entity instanceof ApostleProkaryoteEntity) {
						((ApostleProkaryoteEntity) entity).setAnimation("animation.apostle.skill");
					}
					CaerulaArborMod.queueServerWork(17, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather")), SoundSource.HOSTILE, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather")), SoundSource.HOSTILE, 1, 1, false);
							}
						}
					});
				}
			} else {
				if (entity instanceof ApostleProkaryoteEntity animatable)
					animatable.setTexture("apostle");
			}
		}
	}
}
