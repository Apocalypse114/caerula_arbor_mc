package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class HandHoeSwordProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
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
		boolean validItem = false;
		String rname = "";
		ItemStack item_temp = ItemStack.EMPTY;
		double perc = 0;
		double rate = 0;
		double time = 0;
		double light_cost = 0;
		if (sourceentity instanceof Player && damagesource.is(DamageTypes.PLAYER_ATTACK)) {
			item_temp = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
			if (item_temp.getItem() instanceof HoeItem || item_temp.is(ItemTags.create(new ResourceLocation("minecraft:hoes")))) {
				if ((sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_hand_FERTILITY) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:wipe_magic"))), sourceentity),
							(float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) * 0.1));
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.SQUID_INK, x, y, z, 8, 0.75, 0.9, 0.75, 0.1);
				}
			}
			if (item_temp.getItem() instanceof SwordItem || item_temp.is(ItemTags.create(new ResourceLocation("minecraft:swords")))) {
				if ((sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_hand_SWORD) {
					if (!(entity instanceof LivingEntity _livEnt11 && _livEnt11.hasEffect(CaerulaArborModMobEffects.ROCK_BREAK.get()))) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ROCK_BREAK.get(), 120, 1));
					}
					if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_REACH.get(), 120, 3, false, false));
					LivingHeal1Procedure.execute(sourceentity, (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.1);
				}
			}
		}
	}
}
