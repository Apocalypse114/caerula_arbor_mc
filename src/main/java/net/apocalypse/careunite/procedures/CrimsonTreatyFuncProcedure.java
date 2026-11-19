package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CrimsonTreatyFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		boolean valid = false;
		String regName = "";
		if (entity instanceof Player && (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_TREATY) {
			if (sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:nether_mobs")))) {
				valid = true;
			} else {
				regName = ForgeRegistries.ENTITY_TYPES.getKey(sourceentity.getType()).toString();
				for (String stringiterator : CaerulaConfigsConfiguration.CRIMSON_TREATY.get()) {
					if (IsValidStringProcedure.execute(stringiterator, regName)) {
						valid = true;
						break;
					}
				}
			}
			if (valid) {
				if (sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:bosses")))) {
					if (event instanceof LivingHurtEvent _hurt) {
						_hurt.setAmount((float) (amount * 0.5));
					}
				} else {
					if (event instanceof LivingHurtEvent _hurt) {
						_hurt.setAmount((float) (amount * 0.01));
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.SMOKE, x, (y + 1), z, 16, 1, 1, 1, 0.1);
			}
		}
	}
}
