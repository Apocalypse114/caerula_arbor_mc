package net.apocalypse.careunite.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.MobEffectEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import javax.annotation.Nullable;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;

@Mod.EventBusSubscriber
public class EffectRemoveFuncProcedure {
	public static boolean shouldProtect(MobEffect effect) {
        return effect == CaerulaArborModMobEffects.KINGS_BREATH.get()
        	|| effect == CaerulaArborModMobEffects.SACREFICE.get()
        	|| effect == CaerulaArborModMobEffects.ADD_ATTACK_SPEED_TINY.get()
        	|| effect == CaerulaArborModMobEffects.ADD_DAMAGE_TINY.get()
        	|| effect == CaerulaArborModMobEffects.ADD_DEF_PERCLY_TINY.get()
        	|| effect == CaerulaArborModMobEffects.ADD_DEF_TINY.get()
        	|| effect == CaerulaArborModMobEffects.ADD_MISS_RATE.get()
        	|| effect == CaerulaArborModMobEffects.ADD_RESIS_TINY.get()
        	|| effect == CaerulaArborModMobEffects.ENGRAVED_TRIUMPH.get()
        	|| effect == CaerulaArborModMobEffects.SURVIVORS_GUIDE.get()
        	|| effect == CaerulaArborModMobEffects.UNRIPE_THOUGHTS.get()
        	|| effect == CaerulaArborModMobEffects.HEMOSTATIC.get()
        	|| effect == CaerulaArborModMobEffects.FLAG_SWINGS.get()
        	|| effect == CaerulaArborModMobEffects.KEEP_BEDDING.get()
        	|| effect == CaerulaArborModMobEffects.SPEAR_FIGHT.get()
        	|| effect == CaerulaArborModMobEffects.SPEAR_FIGHT.get()
        	|| effect == CaerulaArborModMobEffects.KINGS_BOOST.get();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onMobEffect(MobEffectEvent.Remove event) {
        if (CaerulaConfigsConfiguration.RELIC_PROTECT.get() 
        	&& event.getEntity() instanceof Player 
        	&& shouldProtect(event.getEffect())) {
            event.setCanceled(true);
        }
    }

	public static void execute(Entity entity, String effect) {
		execute(null, entity, effect);
	}

	private static void execute(@Nullable Event event, Entity entity, String effect) {
		
	}
}
