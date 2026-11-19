package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.item.UninishedBeautyItem;
import net.apocalypse.careunite.CaerulaArborMod;

public class SmashMobsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, ItemStack itemstack) {
		if (entity == null || sourceentity == null)
			return;
		if ((sourceentity instanceof Player _plr ? _plr.getAttackStrengthScale(0) : 0) >= 0.95) {
			if (itemstack.getItem() instanceof UninishedBeautyItem)
				itemstack.getOrCreateTag().putString("geckoAnim", "animation.unfinished_beautuy.attack");
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_cut_spect")), SoundSource.PLAYERS, (float) 2.4, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_cut_spect")), SoundSource.PLAYERS, (float) 2.4, 1, false);
				}
			}
			CaerulaArborMod.queueServerWork(12, () -> {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_spect_1")), SoundSource.PLAYERS, (float) 2.4, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_spect_1")), SoundSource.PLAYERS, (float) 2.4, 1, false);
					}
				}
				new Object() {
					void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
						if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 5 && entity.isAlive() && sourceentity.isAlive()) {
							if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) >= (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)
									/ (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
								entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:saw_cut"))), sourceentity),
										(float) ((sourceentity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity12.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
												* 1));
								if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
									if (sourceentity instanceof LivingEntity _entity)
										_entity.setHealth((float) Math.min((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.025,
												sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1));
								}
							} else {
								entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:saw_cut"))), sourceentity),
										(float) ((sourceentity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity21.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
												* 0.5));
							}
						}
						final int tick2 = ticks;
						CaerulaArborMod.queueServerWork(tick2, () -> {
							if (timedlooptotal > timedloopiterator + 1) {
								timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
							}
						});
					}
				}.timedLoop(0, 10, 1);
			});
		}
	}
}
