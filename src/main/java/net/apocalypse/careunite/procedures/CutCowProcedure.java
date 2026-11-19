package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.OceanizedCowEntity;

public class CutCowProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if (((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(Items.SHEARS) : false) && entity instanceof OceanizedCowEntity _datEntL1 && _datEntL1.getEntityData().get(OceanizedCowEntity.DATA_skill)) {
			if (entity instanceof OceanizedCowEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceanizedCowEntity.DATA_skill, false);
			if (entity instanceof OceanizedCowEntity animatable)
				animatable.setTexture("oceanzied_cow_trailless");
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.mooshroom.shear")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.mooshroom.shear")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 3, 5); index0++) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 0.65), z, new ItemStack(CaerulaArborModBlocks.TRAIL_MUSHROOM.get()));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
			}
			return InteractionResult.SUCCESS;
		} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BUCKET
				&& (entity instanceof OceanizedCowEntity _datEntI ? _datEntI.getEntityData().get(OceanizedCowEntity.DATA_milk_cooldown) : 0) <= 0) {
			(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
			if (sourceentity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(CaerulaArborModItems.NETHERSEA_MILK.get()).copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
			if (entity instanceof OceanizedCowEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedCowEntity.DATA_milk_cooldown, Mth.nextInt(RandomSource.create(), 1200, 2400));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
