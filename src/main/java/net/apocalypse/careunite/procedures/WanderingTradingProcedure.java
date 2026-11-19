package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;

public class WanderingTradingProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		double trd = 0;
		trd = entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_TRADE_LEFT) : 0;
		if (WanderingNotActioningProcedure.execute(entity) && (entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_PHASE) : 0) == 0 && trd > 0) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.REDSTONE_INGOT.get()) {
				if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() >= 16) {
					(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(16);
					if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_TRADE_TICK, 15);
					if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_ACTION_TICK, 15);
					if (entity instanceof OceanizedWanderingTraderEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedWanderingTraderEntity.DATA_TRADE_LEFT, (int) (trd - 1));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.yes")), SoundSource.HOSTILE, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.yes")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
					return InteractionResult.SUCCESS;
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.no")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.no")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
					}
				}
				return InteractionResult.FAIL;
			}
		}
		return InteractionResult.PASS;
	}
}
