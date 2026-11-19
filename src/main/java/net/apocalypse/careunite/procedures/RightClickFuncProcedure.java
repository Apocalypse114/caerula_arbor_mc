package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.ReaperFishEntity;
import net.apocalypse.careunite.entity.OceanizedHorseEntity;
import net.apocalypse.careunite.entity.CreeperFishEntity;
import net.apocalypse.careunite.entity.ChiselerFishEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class RightClickFuncProcedure {
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		String usehand = "";
		if (event.getHand() == InteractionHand.MAIN_HAND) {
			usehand = "mainhand";
		} else {
			usehand = "offhand";
		}
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getTarget(), event.getEntity(), event.getItemStack(), event.getSide() == LogicalSide.CLIENT);
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, ItemStack itemstack, boolean clientside) {
		execute(null, world, x, y, z, entity, sourceentity, itemstack, clientside);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, ItemStack itemstack, boolean clientside) {
		if (entity == null || sourceentity == null)
			return;
		boolean extracted = false;
		if (!clientside) {
			if (itemstack.getItem() == CaerulaArborModItems.OCEAN_EXTRACTOR.get()) {
				if (entity instanceof ReaperFishEntity) {
					extracted = true;
					if (sourceentity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(CaerulaArborModItems.DNA_REAPER.get()).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
				} else if (entity instanceof OceanizedHorseEntity) {
					extracted = true;
					if (sourceentity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(CaerulaArborModItems.DNA_HORSE.get()).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
				} else if (entity instanceof ChiselerFishEntity) {
					extracted = true;
					if (sourceentity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(CaerulaArborModItems.DNA_CHIESLER.get()).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
				} else if (entity instanceof CreeperFishEntity) {
					extracted = true;
					if (sourceentity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(CaerulaArborModItems.DNA_CREEPER.get()).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
				}
				if (extracted) {
					itemstack.shrink(1);
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:extractor_damage"))), sourceentity),
							(float) 0.5);
				}
			} else if (itemstack.getItem() == CaerulaArborModItems.ROCINANTE_INJECTOR.get() && entity instanceof OceanizedHorseEntity) {
				itemstack.shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.husk.converted_to_zombie")), SoundSource.NEUTRAL, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.husk.converted_to_zombie")), SoundSource.NEUTRAL, 2, 1, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, (y + 1), z, 32, 1, 1, 1, 1);
				if (sourceentity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.OCEAN_EXTRACTOR.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				if (!entity.level().isClientSide())
					entity.discard();
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.ROCINANTE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else if (itemstack.getItem() == CaerulaArborModItems.WHIRL_EYE.get() && entity instanceof ElderGuardian) {
				OnWhirlSeaInteractProcedure.execute(world, x, y, z, entity, sourceentity);
			}
		}
	}
}
