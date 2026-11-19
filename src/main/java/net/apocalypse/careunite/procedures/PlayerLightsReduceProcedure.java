package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerLightsReduceProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, damagesource, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		ItemStack weapon = ItemStack.EMPTY;
		Entity sourceent = null;
		String rname = "";
		boolean validweapon = false;
		double dama = 0;
		double handEng = 0;
		if (!event.isCanceled()) {
			if (sourceentity instanceof Player) {
				if (HasEmelightProcedure.execute(sourceentity)) {
					ReducePlayerLightsProcedure.execute(world, sourceentity, Mth.nextDouble(RandomSource.create(), 0.1, 0.2));
				}
				if (HasGlowbodyProcedure.execute(sourceentity)) {
					ReducePlayerLightsProcedure.execute(world, sourceentity, Mth.nextDouble(RandomSource.create(), 0.2, 0.3));
				}
				if (HasResearvhProcedure.execute(sourceentity)) {
					ReducePlayerLightsProcedure.execute(world, sourceentity, Mth.nextDouble(RandomSource.create(), 0.3, 0.5));
				}
				if (HasArmorProcedure.execute(sourceentity)) {
					if (Math.random() < 0.08) {
						ChangePlayerLPOrShieldProcedure.execute(world, sourceentity, true, -1, "collectible.king_armor");
						ChangePlayerLPOrShieldProcedure.execute(world, sourceentity, false, 1, "collectible.king_armor");
					}
				}
				if (HasXrystalProcedure.execute(sourceentity)) {
					if (Math.random() < 0.1) {
						ChangePlayerLPOrShieldProcedure.execute(world, sourceentity, true, -2, "collectible.king_crystal");
						if (sourceentity instanceof Player _player)
							_player.giveExperienceLevels(1);
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.REDSTONE_INGOT.get()));
							entityToSpawn.setPickUpDelay(10);
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
				handEng = (sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_hand_ENGRAVE;
				weapon = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
				if (handEng >= 0 && handEng < 99) {
					if (entity instanceof Monster || (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == sourceentity) {
						validweapon = false;
						if (weapon.getItem() == Items.TRIDENT) {
							validweapon = true;
						} else if (weapon.getItem() instanceof TridentItem) {
							validweapon = true;
						} else if (weapon.is(ItemTags.create(new ResourceLocation("forge:tools/tridents")))) {
							validweapon = true;
						} else if (damagesource.is(DamageTypes.TRIDENT)) {
							validweapon = true;
						} else {
							rname = ForgeRegistries.ITEMS.getKey(weapon.getItem()).toString();
							for (String stringiterator : CaerulaConfigsConfiguration.HAND_ENGRAVE.get()) {
								if (IsValidStringProcedure.execute(stringiterator, rname)) {
									validweapon = true;
									break;
								}
							}
						}
						if (validweapon) {
							{
								double _setval = handEng + 1;
								sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
									capability.relic_hand_ENGRAVE = _setval;
									capability.syncPlayerVariables(sourceentity);
								});
							}
						}
					}
				}
				handEng = (sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_SURVIVOR;
				if (handEng >= 0 && handEng < 32) {
					if (entity instanceof Monster || (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == sourceentity) {
						if (Math.random() < 0.035 || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:bosses")))) {
							{
								double _setval = handEng + 1;
								sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
									capability.relic_SURVIVOR = _setval;
									capability.syncPlayerVariables(sourceentity);
								});
							}
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.WAX_ON, x, y, z, 48, 0.7, 1.5, 0.7, 0.2);
						}
					}
				}
				if (weapon.is(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:self_mendable")))) {
					dama = weapon.getDamageValue() - Mth.nextInt(RandomSource.create(), 1, (int) (5 + weapon.getEnchantmentLevel(Enchantments.UNBREAKING)));
					if (dama <= 0) {
						(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).setDamageValue(0);
					} else {
						(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).setDamageValue((int) dama);
					}
				}
			}
		}
	}
}
