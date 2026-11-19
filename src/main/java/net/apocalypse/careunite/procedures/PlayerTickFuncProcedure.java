package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModEnchantments;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class PlayerTickFuncProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player.level(), event.player.getX(), event.player.getY(), event.player.getZ(), event.player);
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean valid = false;
		double modifi = 0;
		double suitKing = 0;
		double amplifi = 0;
		double suitArchfi = 0;
		double enchant = 0;
		ItemStack mainHandItem = ItemStack.EMPTY;
		TriggerEchoProcedure.execute(world, x, y, z, entity);
		if (entity.tickCount % 10 != 0) {
			return;
		}
		if (entity.tickCount % 20 == 0) {
			for (int index0 = 0; index0 < 8; index0++) {
				ReduceEcoTimeProcedure.execute(entity, index0);
			}
		}
		OnPlayerEchoTickProcedure.execute(world, entity);
		OnGoldenChaliseTickProcedure.execute(entity);
		if (new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
				}
				return false;
			}
		}.checkGamemode(entity)) {
			if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
				_livingEntity3.getAttribute(CaerulaArborModAttributes.NUMB.get()).setBaseValue(0);
		}
		if (IsRejectionOnProcedure.execute(entity, 1)) {
			if (!(entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(CaerulaArborModMobEffects.HAEMOPHILIA.get()))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.HAEMOPHILIA.get(), 200, 1, false, false));
			}
		} else {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.HAEMOPHILIA.get());
		}
		if (IsRejectionOnProcedure.execute(entity, 3)) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FLESHDEFORMITY.get(), 200, 1, false, false));
		} else {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.FLESHDEFORMITY.get());
		}
		if (IsRejectionOnProcedure.execute(entity, 0) && IsRejectionOnProcedure.execute(entity, 1) && IsRejectionOnProcedure.execute(entity, 2) && IsRejectionOnProcedure.execute(entity, 3)) {
			if (entity instanceof ServerPlayer _player) {
				Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:to_dance_with_we_many"));
				AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
				if (!_ap.isDone()) {
					for (String criteria : _ap.getRemainingCriteria())
						_player.getAdvancements().award(_adv, criteria);
				}
			}
		}
		modifi = 1;
		if (LightIsDimProcedure.execute(entity)) {
			modifi = 1.2;
		} else if (LightIsCeasedProcedure.execute(entity)) {
			modifi = 1.5;
		}
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization >= 3) {
			modifi = modifi * 0.33;
		}
		if (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
			_livingEntity10.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(modifi);
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_lives <= 1) {
			suitKing = 0;
			if (HasSpearProcedure.execute(entity)) {
				suitKing = suitKing + 1;
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.KINGS_BOOST.get(), 20, 1, false, false));
			}
			if (HasArmorProcedure.execute(entity)) {
				suitKing = suitKing + 1;
			}
			if (HasXrystalProcedure.execute(entity)) {
				suitKing = suitKing + 1;
			}
			if (HasExtensionProcedure.execute(entity)) {
				suitKing = suitKing + 1;
				amplifi = Math.ceil(Math.log((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) / 10) / Math.log(2));
				if (amplifi > 24) {
					amplifi = 24;
				}
				if (!(entity instanceof LivingEntity _livEnt13 && _livEnt13.hasEffect(MobEffects.REGENERATION))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, (int) (amplifi - 1), false, false));
				}
			}
			if (HasCrownProcedure.execute(entity)) {
				suitKing = suitKing + 1;
				if (suitKing < 3) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.KINGS_BREATH.get(), 20, 0, false, false));
					{
						double _setval = 1;
						entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.player_king_suit = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.KINGS_BREATH.get(), 20, 2, false, false));
					{
						double _setval = 2;
						entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.player_king_suit = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				}
			} else {
				{
					double _setval = 0;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.player_king_suit = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
		mainHandItem = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_hand_SPEED) {
			if (mainHandItem.getItem() instanceof PickaxeItem || mainHandItem.is(ItemTags.create(new ResourceLocation("minecraft:pickaxes")))) {
				valid = true;
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator == entity) {
							continue;
						}
						if (entityiterator instanceof ServerPlayer || entityiterator instanceof Player || entityiterator instanceof Animal) {
							valid = false;
						}
					}
				}
				if (valid) {
					if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.DIG_SPEED) ? _livEnt.getEffect(MobEffects.DIG_SPEED).getAmplifier() : 0) < 2) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, 2));
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.HANDS_SPEED.get(), 20, 2));
				}
			}
		}
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_lives >= (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new CaerulaArborModVariables.PlayerVariables())).player_maxlive) {
			suitArchfi = 0;
			if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_archfi_FLAG) {
				suitArchfi = suitArchfi + 1;
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FLAG_SWINGS.get(), 20, 2, false, false));
			}
			if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_archfi_BED) {
				suitArchfi = suitArchfi + 1;
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.KEEP_BEDDING.get(), 20, 0, false, false));
			}
			if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_archfi_ARTIFACT) {
				suitArchfi = suitArchfi + 1;
				if (suitArchfi < 3) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SACREFICE.get(), 20, 0, false, false));
					{
						double _setval = 1;
						entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.player_demon_suit = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SACREFICE.get(), 20, 2, false, false));
					{
						double _setval = 2;
						entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.player_demon_suit = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				}
			} else {
				{
					double _setval = 0;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.player_demon_suit = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_hand_ENGRAVE > 0) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ENGRAVED_TRIUMPH.get(), 20,
						(int) ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_hand_ENGRAVE - 1), false, false));
		}
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_SURVIVOR > 0) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SURVIVORS_GUIDE.get(), 20,
						(int) ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_SURVIVOR - 1), false, false));
		}
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_lives > (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new CaerulaArborModVariables.PlayerVariables())).player_maxlive) {
			{
				double _setval = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_maxlive;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.player_lives = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_lives < 1) {
			{
				double _setval = 1;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.player_lives = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		if ((entity instanceof LivingEntity _livingEntity34 && _livingEntity34.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()) ? _livingEntity34.getAttribute(CaerulaArborModAttributes.SANITY.get()).getBaseValue() : 0) > 1000) {
			if (entity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()))
				_livingEntity35.getAttribute(CaerulaArborModAttributes.SANITY.get()).setBaseValue(1000);
		}
		enchant = 0;
		if (EnchantmentHelper.getItemEnchantmentLevel(CaerulaArborModEnchantments.SANITY_DEFEND.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0) {
			enchant = enchant + (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getEnchantmentLevel(CaerulaArborModEnchantments.SANITY_DEFEND.get());
		}
		if (EnchantmentHelper.getItemEnchantmentLevel(CaerulaArborModEnchantments.SANITY_DEFEND.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)) != 0) {
			enchant = enchant + (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getEnchantmentLevel(CaerulaArborModEnchantments.SANITY_DEFEND.get());
		}
		if (EnchantmentHelper.getItemEnchantmentLevel(CaerulaArborModEnchantments.SANITY_DEFEND.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)) != 0) {
			enchant = enchant + (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getEnchantmentLevel(CaerulaArborModEnchantments.SANITY_DEFEND.get());
		}
		if (EnchantmentHelper.getItemEnchantmentLevel(CaerulaArborModEnchantments.SANITY_DEFEND.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)) != 0) {
			enchant = enchant + (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getEnchantmentLevel(CaerulaArborModEnchantments.SANITY_DEFEND.get());
		}
		if (enchant > 16) {
			enchant = 16;
		}
		if (enchant > 0 && !(entity instanceof LivingEntity _livEnt52 && _livEnt52.hasEffect(CaerulaArborModMobEffects.SANIDY_DEFENDER.get()))) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SANIDY_DEFENDER.get(), 20, (int) (enchant - 1), false, false));
		}
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization >= 3) {
			if (entity.isUnderWater()) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 20, 0));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0));
			}
		}
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_HEMOST) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.HEMOSTATIC.get(), 20, 0, false, false));
		}
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_YEARNING) {
			if (!((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem())) {
				amplifi = Math.min(Math.floor((entity instanceof Player _plr ? _plr.experienceLevel : 0) * 0.25), 64);
				if (amplifi >= 1) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.UNRIPE_THOUGHTS.get(), 20, (int) amplifi, false, false));
				}
			}
		}
		SetLPLimitProcedure.execute(entity);
	}
}
