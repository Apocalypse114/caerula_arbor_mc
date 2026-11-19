package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.NautilusHeadhunterEntity;

public class NautilusSKillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double dryTick = 0;
		boolean isMounting = false;
		Entity enemy = null;
		Entity vehicle = null;
		if (!entity.isAlive()) {
			return;
		}
		dryTick = entity instanceof NautilusHeadhunterEntity _datEntI ? _datEntI.getEntityData().get(NautilusHeadhunterEntity.DATA_DRY_TICK) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		isMounting = entity.isPassenger();
		if (entity.isInWaterRainOrBubble() || isMounting) {
			if (entity instanceof NautilusHeadhunterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(NautilusHeadhunterEntity.DATA_DRY_TICK, 0);
		} else {
			if (entity instanceof NautilusHeadhunterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(NautilusHeadhunterEntity.DATA_DRY_TICK, (int) (dryTick + 1));
		}
		if (entity.tickCount % 20 == 5) {
			if (dryTick > 300) {
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.DRY_OUT)), (float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.05));
			}
			if (isMounting) {
				vehicle = entity.getVehicle();
				if (!(vehicle == null) && vehicle.isAlive()) {
					if (!world.isClientSide()) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.eat")), SoundSource.HOSTILE, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.eat")), SoundSource.HOSTILE, 1, 1, false);
							}
						}
					}
					vehicle.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.IN_WALL), entity),
							(float) (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity16.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					DeductPlayerSanityProcedure.execute(vehicle, 50);
				}
			} else {
				if (!(enemy == null) && enemy.isAlive() && !(enemy instanceof Player)) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 2 && !enemy.isVehicle()) {
						if (!world.isClientSide()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.strider.saddle")), SoundSource.HOSTILE, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.strider.saddle")), SoundSource.HOSTILE, 1, 1, false);
								}
							}
						}
						entity.startRiding(enemy);
					}
				}
			}
		}
	}
}
