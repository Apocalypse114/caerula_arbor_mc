package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEnchantments;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class PlayerHitFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, entity, immediatesourceentity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity, double amount) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		boolean validItem = false;
		ItemStack item_temp = ItemStack.EMPTY;
		ItemStack mainHandItem = ItemStack.EMPTY;
		String rname = "";
		double rate = 0;
		double time = 0;
		double perc = 0;
		mainHandItem = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		if (EnchantmentHelper.getItemEnchantmentLevel(CaerulaArborModEnchantments.MUTE_ATTACK.get(), mainHandItem) != 0) {
			if (Math.random() < 0.2 * mainHandItem.getEnchantmentLevel(CaerulaArborModEnchantments.MUTE_ATTACK.get())) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MUTE.get(), (int) (30 * mainHandItem.getEnchantmentLevel(CaerulaArborModEnchantments.MUTE_ATTACK.get())), 0, false, false));
			}
		}
		if (!event.isCanceled()) {
			if (entity instanceof Player) {
				if (IsRejectionOnProcedure.execute(entity, 2)) {
					rate = 0.04;
					time = 40;
					if (world.getBiome(BlockPos.containing(x, y, z)).value().getBaseTemperature() * 100f >= 180) {
						rate = 0.01;
						time = 20;
					} else if (world.getBiome(BlockPos.containing(x, y, z)).value().getBaseTemperature() * 100f <= 10) {
						rate = 0.06;
						time = 80;
					}
					if (Math.random() < rate) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FROZEN.get(), (int) time, 0, false, false));
					}
				}
			}
			if (sourceentity instanceof Player) {
				if (!(immediatesourceentity == sourceentity)) {
					if ((sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_hand_STRANGLE) {
						rname = ForgeRegistries.ITEMS.getKey(mainHandItem.getItem()).toString();
						validItem = false;
						if (mainHandItem.is(ItemTags.create(new ResourceLocation("forge:tools/crossbows")))) {
							validItem = true;
						} else if (mainHandItem.getItem() instanceof CrossbowItem) {
							validItem = true;
						} else {
							for (String stringiterator : CaerulaConfigsConfiguration.HAND_STRANGLE.get()) {
								if (IsValidStringProcedure.execute(stringiterator, rname)) {
									validItem = true;
									break;
								}
							}
						}
						if (validItem && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.25) {
							if (entity.isAlive()) {
								entity.hurt(
										new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hand_of_choker"))), sourceentity),
										(float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 99));
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.GLOW_SQUID_INK, (entity.getX()), (entity.getY()), (entity.getZ()), 128, 1, 1, 1, 0.33);
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.hurt")), SoundSource.NEUTRAL, 2, 1);
									} else {
										_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.hurt")), SoundSource.NEUTRAL, 2, 1, false);
									}
								}
							}
						}
					}
					if ((sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_hand_FIREWORK) {
						validItem = false;
						rname = ForgeRegistries.ITEMS.getKey(mainHandItem.getItem()).toString();
						if (mainHandItem.getItem() == Items.BOW) {
							validItem = true;
						} else if (mainHandItem.getItem() instanceof BowItem) {
							validItem = true;
						} else if (mainHandItem.getItem() == CaerulaArborModItems.PHLOEM_BOW.get()) {
							validItem = true;
						} else if (mainHandItem.is(ItemTags.create(new ResourceLocation("forge:tools/bows")))) {
							validItem = true;
						} else {
							for (String stringiterator : CaerulaConfigsConfiguration.HAND_FIREWORK.get()) {
								if (IsValidStringProcedure.execute(stringiterator, rname)) {
									validItem = true;
									break;
								}
							}
						}
						if (validItem && Math.random() < 0.33) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.launch")),
											SoundSource.PLAYERS, (float) 3.6, 1);
								} else {
									_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.launch")), SoundSource.PLAYERS,
											(float) 3.6, 1, false);
								}
							}
							CaerulaArborMod.queueServerWork(10, () -> {
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.FIREWORK, x, y, z, 85, 2, 2, 2, 0.22);
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.twinkle")), SoundSource.PLAYERS, (float) 3.6, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.twinkle")), SoundSource.PLAYERS, (float) 3.6, 1, false);
									}
								}
								{
									final Vec3 _center = new Vec3(x, y, z);
									List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
											.toList();
									for (Entity entityiterator : _entfound) {
										if (((ForgeRegistries.ENTITY_TYPES.getKey(entityiterator.getType()).toString()).equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()) || entityiterator instanceof Monster)
												&& !(entityiterator == sourceentity)) {
											entityiterator.hurt(
													new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hand_firework"))),
															sourceentity),
													(float) (amount * 3));
										}
									}
								}
							});
						}
					}
				}
				if ((sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_legend_CHITIN) {
					if (Math.random() < 0.05) {
						{
							ItemStack _setval = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
							sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.chitin_knife_selected = _setval.copy();
								capability.syncPlayerVariables(sourceentity);
							});
						}
						perc = GetHealthPercProcedure.execute(sourceentity);
						if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.TIDE_OF_CHITIN.get(), 500, 0, false, false));
						if (perc > 0) {
							if (sourceentity instanceof LivingEntity _entity)
								_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * perc));
						}
						if (world instanceof Level _level) {
							if (_level.isClientSide()) {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.beacon.activate")), SoundSource.NEUTRAL, (float) 3.2, 1, false);
							}
						}
					}
				}
			}
		}
	}
}
