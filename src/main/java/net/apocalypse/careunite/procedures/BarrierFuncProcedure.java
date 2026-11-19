package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.apocalypse.careunite.CaerulaEvents;
import net.minecraftforge.common.MinecraftForge;
import net.apocalypse.careunite.init.CaerulaArborModSounds;

@Mod.EventBusSubscriber
public class BarrierFuncProcedure {
	@SubscribeEvent
(priority = EventPriority.LOW)
	public static void onEntityAttacked(LivingHurtEvent event) {
		DamageSource damagesource = event.getSource();
		LivingEntity living = event.getEntity();
		Level level = living.level();
		if (damagesource == null || living == null)
 return;
		living.getPersistentData().putDouble("caerula.lastHurtByTime", (double)living.tickCount);
		if (damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || damagesource.is(DamageTypeTags.BYPASSES_EFFECTS)) {
			return;
		}
		float amount = event.getAmount();
		float newAmount = amount;
		if (newAmount > 0){
			newAmount = HandleLayeredBarrier(living, newAmount, level, damagesource);
		}
		if (newAmount > 0){
			newAmount = (float) OnLivingBarrierFuncProcedure.execute(damagesource, living, (float) newAmount);
			newAmount = handleLivingBarrier(living, newAmount, level, damagesource);
		}
		if (newAmount < amount) event.setAmount(newAmount);
	}

	private static float handleLivingBarrier(LivingEntity living, float amount, Level level, DamageSource damagesource){
		double x = living.getX(), y = living.getY(), z = living.getZ();
		AttributeInstance instance = living.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get());
		if (instance == null) return amount;
		double brr = instance.getBaseValue();
		double disp = 0;
		float finalAmount = amount;
		CaerulaEvents.LivingBarrierBlockEvent ev = new CaerulaEvents.LivingBarrierBlockEvent(living, damagesource, amount);
		MinecraftForge.EVENT_BUS.post(ev);
		amount = (float)ev.getAmount();
		if (brr > 0) {
			if (brr >= amount) {
				instance.setBaseValue(brr - amount);
				disp = amount;
				finalAmount = 0;
			} else {
				instance.setBaseValue(0);
				disp = brr;
				finalAmount = amount - (float) brr;
			}
			float pitch = (float) Mth.nextDouble(level.random, 0.9, 1.1);
			
			if (!level.isClientSide()) {
				level.playSound(null, BlockPos.containing(x, y, z), CaerulaArborModSounds.LIVING_BARRIER.get(), SoundSource.HOSTILE, 2, pitch);
			} else {
				level.playLocalSound(x, y, z, CaerulaArborModSounds.LIVING_BARRIER.get(), SoundSource.HOSTILE, 2, pitch, false);
			}
			if (level instanceof ServerLevel _level){
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.LIVING_BARRIER_SHOW.get()), x, (y + 0.75), z, (int) Math.min(disp * 0.5, 24), 0.75, 0.75, 0.75, 0.1);
			}
		}
		return finalAmount;
	}

	private static float HandleLayeredBarrier(LivingEntity living, float amount, Level level, DamageSource damagesource){
		double x = living.getX(), y = living.getY(), z = living.getZ();
		AttributeInstance instance = living.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get());
		if (instance == null) return amount;
		double brr = instance.getBaseValue();
		if (brr > 0) {
			CaerulaEvents.LayeredBarrierBlockEvent ev = new CaerulaEvents.LayeredBarrierBlockEvent(living, damagesource,1);
			MinecraftForge.EVENT_BUS.post(ev);
			int l = ev.getAmount();
			if (brr >= l){
				instance.setBaseValue(brr - l);
				float pitch = (float) Mth.nextDouble(level.random, 0.9, 1.1);
				if (!level.isClientSide()) {
					level.playSound(null, BlockPos.containing(x, y, z), CaerulaArborModSounds.LAYERED_BARRIER.get(), SoundSource.HOSTILE, 2, pitch);
				} else {
					level.playLocalSound(x, y, z, CaerulaArborModSounds.LAYERED_BARRIER.get(), SoundSource.HOSTILE, 2, pitch, false);
				}
				return 0;
			}
		}
		return amount;
	}
}
