package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class NumbnessFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		execute(null, world, x, y, z, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double numb = 0;
		if (!event.isCanceled()) {
			numb = sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()) ? _livingEntity1.getAttribute(CaerulaArborModAttributes.NUMB.get()).getBaseValue() : 0;
			if (numb > 0) {
				if (sourceentity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
					_livingEntity2.getAttribute(CaerulaArborModAttributes.NUMB.get()).setBaseValue((numb - 1));
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NUMBNESS.get()), (sourceentity.getX()), (sourceentity.getY() + 1), (sourceentity.getZ()), 1, 1, 1, 1, 0.1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:numbness")), SoundSource.HOSTILE, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:numbness")), SoundSource.HOSTILE, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
				if (event != null && event.isCancelable()) {
					event.setCanceled(true);
				}
			}
		}
	}
}
