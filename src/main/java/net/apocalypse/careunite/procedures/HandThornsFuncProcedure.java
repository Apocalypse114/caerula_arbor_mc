package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class HandThornsFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getSource(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, DamageSource damagesource, Entity entity, Entity sourceentity) {
		execute(null, world, damagesource, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity, Entity sourceentity) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		if (entity instanceof Player && entity.tickCount - (entity instanceof LivingEntity _livEnt ? _livEnt.getLastHurtByMobTimestamp() : 0) >= 5) {
			if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_hand_THORNS) {
				if (!entity.isShiftKeyDown() && sourceentity.isAlive() && entity.isAlive()) {
					if (!(sourceentity instanceof Player)) {
						if (!damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hand_spike"))) && !damagesource.is(DamageTypes.THORNS)
								&& !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:gunmu_damage"))) && !(entity == sourceentity)) {
							CaerulaArborMod.queueServerWork(2, () -> {
								if (!(sourceentity instanceof TamableAnimal _tamIsTamedBy && entity instanceof LivingEntity _livEnt ? _tamIsTamedBy.isOwnedBy(_livEnt) : false)) {
									if (world instanceof ServerLevel _level)
										_level.sendParticles(ParticleTypes.SMOKE, (sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), 72, 0.85, 1, 0.85, 0.2);
									sourceentity.hurt(
											new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hand_spike"))), entity),
											entity instanceof LivingEntity _livEnt ? _livEnt.getArmorValue() : 0);
								}
							});
						}
					}
				}
			}
		}
	}
}
