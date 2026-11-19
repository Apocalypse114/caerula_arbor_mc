package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MobFinalHurtFuncProcedure {
	@SubscribeEvent
	public static void entityTakesDamage(LivingDamageEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, double amount) {
		execute(null, world, x, y, z, damagesource, entity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return;
		Entity sss = null;
		sss = damagesource.getEntity();
		if (sss instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.ATTACK_COUNTER_BACK.get())) {
			sss.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_real")))),
					(float) (Math.min(amount, entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.33));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("enchant.thorns.hit")), SoundSource.NEUTRAL, 2, Mth.nextInt(RandomSource.create(), (int) 0.75, (int) 1.2));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("enchant.thorns.hit")), SoundSource.NEUTRAL, 2, Mth.nextInt(RandomSource.create(), (int) 0.75, (int) 1.2), false);
				}
			}
		}
		PlayerRejectionFuncProcedure.execute(world, x, y, z, entity, sss, amount);
		PlayerEvoFinalHurtProcedure.execute(world, x, y, z, entity, sss, amount);
		if (entity instanceof Player) {
			ReduceLightsWithDamageProcedure.execute(world, entity, sss, amount);
			OnEchoFinalHurtProcedure.execute(world, entity, amount);
		}
	}
}
