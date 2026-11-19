package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.item.LegendarySpearItem;
import net.apocalypse.careunite.init.CaerulaArborModEnchantments;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class ExtraAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, ItemStack itemstack) {
		if (entity == null || sourceentity == null)
			return;
		if (!(new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(entity))) {
			{
				ItemStack _ist = itemstack;
				if (_ist.hurt(1, RandomSource.create(), null)) {
					_ist.shrink(1);
					_ist.setDamageValue(0);
				}
			}
		}
		if (!(sourceentity instanceof Player _plrCldCheck4 && _plrCldCheck4.getCooldowns().isOnCooldown(itemstack.getItem()))
				&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
			if (sourceentity.isShiftKeyDown()) {
				if (itemstack.getItem() instanceof LegendarySpearItem)
					itemstack.getOrCreateTag().putString("geckoAnim", "animation.lengendspear.swing2");
				if (sourceentity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), 25);
				CaerulaArborMod.queueServerWork(10, () -> {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.NEUTRAL, (float) 3.5, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.NEUTRAL, (float) 3.5, 1, false);
						}
					}
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(7 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (entityiterator instanceof Monster || (entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == sourceentity) {
								if (entityiterator.isAlive() && !(entityiterator == sourceentity)) {
									if ((sourceentity != null ? entityiterator.distanceTo(sourceentity) : -1) <= 3) {
										entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.TRIDENT), sourceentity),
												(float) ((sourceentity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE)
														? _livingEntity20.getAttribute(Attributes.ATTACK_DAMAGE).getValue()
														: 0) * (1 + 0.2 * itemstack.getEnchantmentLevel(CaerulaArborModEnchantments.SYNESTHESIA.get()))));
									}
								}
							}
						}
					}
				});
			} else if (sourceentity.getDeltaMovement().y() < -0.1) {
				if (itemstack.getItem() instanceof LegendarySpearItem)
					itemstack.getOrCreateTag().putString("geckoAnim", "animation.lengendspear.srike");
				if (sourceentity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), 25);
				CaerulaArborMod.queueServerWork(10, () -> {
					if (entity.isAlive()) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.hit_ground")), SoundSource.NEUTRAL, (float) 3.5, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.hit_ground")), SoundSource.NEUTRAL, (float) 3.5, 1, false);
							}
						}
						if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 4) {
							entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.TRIDENT), sourceentity),
									(float) ((sourceentity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity35.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
											* (1 + 0.2 * itemstack.getEnchantmentLevel(CaerulaArborModEnchantments.SYNESTHESIA.get()))));
						}
					}
				});
			} else if (sourceentity.getDeltaMovement().y() > 0.1) {
				if (itemstack.getItem() instanceof LegendarySpearItem)
					itemstack.getOrCreateTag().putString("geckoAnim", "animation.lengendspear.swing");
				if (sourceentity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), 25);
				CaerulaArborMod.queueServerWork(10, () -> {
					if (entity.isAlive()) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.throw")), SoundSource.NEUTRAL, (float) 3.5, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.throw")), SoundSource.NEUTRAL, (float) 3.5, 1, false);
							}
						}
						if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 4) {
							entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.TRIDENT), sourceentity),
									(float) ((sourceentity instanceof LivingEntity _livingEntity49 && _livingEntity49.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity49.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
											* (1 + 0.2 * itemstack.getEnchantmentLevel(CaerulaArborModEnchantments.SYNESTHESIA.get()))));
							entity.push(0, 0.5, 0);
						}
					}
				});
			} else {
				if (itemstack.getItem() instanceof LegendarySpearItem)
					itemstack.getOrCreateTag().putString("geckoAnim", "animation.lengendspear.stab");
				if (sourceentity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), 25);
				CaerulaArborMod.queueServerWork(10, () -> {
					if (entity.isAlive()) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.hit")), SoundSource.NEUTRAL, (float) 3.5, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.hit")), SoundSource.NEUTRAL, (float) 3.5, 1, false);
							}
						}
						if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 4) {
							entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.TRIDENT), sourceentity),
									(float) ((sourceentity instanceof LivingEntity _livingEntity63 && _livingEntity63.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity63.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
											* (1 + 0.2 * itemstack.getEnchantmentLevel(CaerulaArborModEnchantments.SYNESTHESIA.get()))));
							entity.push((sourceentity.getLookAngle().x), 0, (sourceentity.getLookAngle().z));
						}
					}
				});
			}
		}
	}
}
