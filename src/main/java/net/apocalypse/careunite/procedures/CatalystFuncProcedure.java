package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CatalystFuncProcedure {
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
		double perc = 0;
		double dama = 0;
		if (clientside) {
			return;
		}
		if (!entity.isAlive()) {
			return;
		}
		if (!(entity instanceof Mob)) {
			return;
		}
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:bosses")))
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) * 4.5) {
			return;
		}
		if (itemstack.getItem() == CaerulaArborModItems.OCEANIZE_CATALYST.get()) {
			perc = 1 - (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:cannot_transform"))) || entity instanceof LivingEntity _livEnt10 && _livEnt10.getMobType() == MobType.UNDEAD
					|| entity instanceof LivingEntity _livEnt11 && _livEnt11.isBaby()) {
				return;
			}
			if (Math.random() < perc + 0.05 && TransformIndexProcedure.execute(world, x, y, z, entity)) {
				if (!entity.level().isClientSide())
					entity.discard();
				itemstack.shrink(1);
			} else {
				dama = Math.min((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) * 0.33, (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) * 1.5);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lava.extinguish")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lava.extinguish")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:extractor_damage")))), (float) 0.5);
				DeductPlayerSanityProcedure.execute(entity, 256);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) Math.max((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) - dama, 0.5));
				itemstack.shrink(1);
			}
		}
	}
}
