package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.LittleHelperEntity;
import net.apocalypse.careunite.entity.Al1SHelperEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class LittleHelperNoHurtProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean isAl1s = false;
		double dur = 0;
		double tgtX = 0;
		double tgtY = 0;
		double tgtZ = 0;
		if (entity instanceof LittleHelperEntity || entity instanceof Al1SHelperEntity) {
			if (event != null && event.isCancelable()) {
				event.setCanceled(true);
			}
			isAl1s = entity instanceof LittleHelperEntity;
			if (isAl1s) {
				dur = entity instanceof LittleHelperEntity _datEntI ? _datEntI.getEntityData().get(LittleHelperEntity.DATA_durability) : 0;
			} else {
				dur = entity instanceof Al1SHelperEntity _datEntI ? _datEntI.getEntityData().get(Al1SHelperEntity.DATA_durability) : 0;
			}
			if (dur > 0) {
				dur = dur - 1;
				if (entity instanceof LittleHelperEntity _datEntSetI)
					_datEntSetI.getEntityData().set(LittleHelperEntity.DATA_durability, (int) dur);
				if (entity instanceof Al1SHelperEntity _datEntSetI)
					_datEntSetI.getEntityData().set(Al1SHelperEntity.DATA_durability, (int) dur);
			} else {
				if (dur >= 0 && entity.isAlive()) {
					if (!entity.level().isClientSide())
						entity.discard();
					if (isAl1s) {
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.ITEM_HELPER.get()));
							entityToSpawn.setPickUpDelay(10);
							entityToSpawn.setUnlimitedLifetime();
							_level.addFreshEntity(entityToSpawn);
						}
					} else {
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.ITEM_HELPER_AL_1S.get()));
							entityToSpawn.setPickUpDelay(10);
							entityToSpawn.setUnlimitedLifetime();
							_level.addFreshEntity(entityToSpawn);
						}
						if (!world.isClientSide()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:al1s_break")), SoundSource.BLOCKS, 3, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:al1s_break")), SoundSource.BLOCKS, 3, 1, false);
								}
							}
						}
					}
				}
			}
		}
	}
}
