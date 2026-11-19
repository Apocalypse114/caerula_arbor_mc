package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.CaerulaArborMod;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import net.minecraftforge.eventbus.api.EventPriority;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.Difficulty;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;

@Mod.EventBusSubscriber
public class LifePointFuncProcedure {

	public static final TagKey<DamageType> RARE = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:rare"));
	public static final TagKey<DamageType> HORROR = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:horror"));
	public static final TagKey<DamageType> BYPASS = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:bypass_protection"));

	public static final TagKey<EntityType<?>> CONSUME_II = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:lp/consume_two"));
	public static final TagKey<EntityType<?>> CONSUME_III = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:lp/consume_three"));
	public static final TagKey<EntityType<?>> CONSUME_V = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:lp/consume_five"));
	public static final TagKey<EntityType<?>> CONSUME_XII = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:lp/consume_dozen"));

	public static final TagKey<EntityType<?>> BOSS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:bosses"));
	
	public static final ResourceLocation LOSS = new ResourceLocation("caerula_arbor_reunite:target_damaged");

	public static SoundEvent lossSound(){
		return ForgeRegistries.SOUND_EVENTS.getValue(LOSS);
	}

	public static boolean shouldLog(){
		return CaerulaConfigsConfiguration.DEBUG.get();
	}

	public static int getReinforcedLPConsume(DamageSource source){
		Entity directEntity = source.getEntity();
		if (directEntity != null && directEntity.isAlive()){
			EntityType type = directEntity.getType();
			if (type.is(CONSUME_II)) return 2;
			if (type.is(CONSUME_III)) return 3;
			if (type.is(CONSUME_V)) return 5;
			if (type.is(CONSUME_XII)) return 12;
			if (type.is(BOSS)) return 5;
		}
		return 1;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onEntityDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if(entity == null)return;
		LevelAccessor world = entity.level();
		DamageSource damagesource = event.getSource();
		if(damagesource == null) return;
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		boolean death_blocked = false;
		boolean is_shield = false;
		boolean should_func = world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.TARGET_LIFE_FUNCTION);
		boolean reduceMore = CaerulaConfigsConfiguration.LP_CONSUME.get();
		double light_cost = 0;
		if (entity instanceof Player && !event.isCanceled()) {
			if (damagesource.is(BYPASS)) {
				return;
			}
			if (!should_func) {
				light_cost = 10;
			} else {
				int lp_cost_raw = 1;
				if (reduceMore){
					lp_cost_raw = getReinforcedLPConsume(damagesource);
					CaerulaArborMod.LOGGER.info("LP cost boost to {} due to {}", lp_cost_raw, damagesource.toString());
				}
				lp_cost_raw *= -1;
				if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElseGet(CaerulaArborModVariables.PlayerVariables::new)).player_shield > 0) {
					is_shield = true;
					death_blocked = ChangePlayerLPOrShieldProcedure.execute(entity.level(), entity, false, lp_cost_raw, "death");
				} else if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElseGet(CaerulaArborModVariables.PlayerVariables::new)).player_lives > 1) {
					death_blocked = ChangePlayerLPOrShieldProcedure.execute(entity.level(), entity, true, lp_cost_raw, "death");
				}
			}
			if (death_blocked) {
				event.setCanceled(true);
				GiveSecondBreathAdvProcedure.execute(entity);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, (float) 0.33, 1);
						_level.playSound(null, BlockPos.containing(x, y, z), lossSound(), SoundSource.PLAYERS, (float) 0.33, 1);
					} else {
						_level.playLocalSound(x, y, z, SoundEvents.TOTEM_USE, SoundSource.PLAYERS, (float) 0.33, 1, false);
						_level.playLocalSound(x, y, z, lossSound(), SoundSource.PLAYERS, (float) 0.33, 1, false);
					}
				}
				if (entity.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()))
					entity.getAttribute(CaerulaArborModAttributes.SANITY.get()).setBaseValue(1000);
				if (is_shield) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.SHIELDLOSS.get()), x, (y + 0.95), z, 72, 0.75, 0.55, 0.75, 0.2);
					if (!world.isClientSide()){
						entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 80, 0));
					}
					if (world.getDifficulty() == Difficulty.HARD)
						entity.setHealth(entity.getMaxHealth() * 0.5f);
					else if (world.getDifficulty() == Difficulty.NORMAL)
						entity.setHealth(entity.getMaxHealth() * 0.8f);
					else entity.setHealth(entity.getMaxHealth());
				} else {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.LIFELOSS.get()), x, (y + 0.95), z, 72, 0.75, 0.55, 0.75, 0.2);
					if (world.getDifficulty() == Difficulty.HARD)
						entity.setHealth(entity.getMaxHealth() * 0.2f);
					else if (world.getDifficulty() == Difficulty.NORMAL)
						entity.setHealth(entity.getMaxHealth() * 0.4f);
					else entity.setHealth(entity.getMaxHealth() * 0.5f);
					if (damagesource.is(RARE)) {
						light_cost = 15;
					} else if (damagesource.is(HORROR)) {
						light_cost = 10;
					} else {
						light_cost = 5;
					}
				}
			} else {
				light_cost = 50;
				PlayerDiedInOceanizationProcedure.execute(
					entity.level(), entity.getX(), entity.getY(), entity.getZ(), damagesource, entity);
			}
			if (light_cost > 0) {
				ReducePlayerLightsProcedure.execute(entity.level(), entity, light_cost);
			}
		}
	}
}
