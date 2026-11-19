package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.OceanizedChickenEntity;
import net.apocalypse.careunite.CaerulaArborMod;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.network.syncher.SynchedEntityData;

public class CrackEggProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity, ItemStack itemstack) {
		if (direction == null || entity == null)
			return InteractionResult.SUCCESS;
		if (world.isClientSide()) return InteractionResult.SUCCESS;
		if (Math.random() < 0.75) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sniffer_egg.crack")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sniffer_egg.crack")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			return InteractionResult.FAIL;
		}
		int count = (int) (1 + Math.pow(1.45 * Math.random(), 2));
		double rrr = Math.max(1, itemstack.getOrCreateTag().getDouble("rate") * 0.001);
		double ooo = Math.max(4, itemstack.getOrCreateTag().getDouble("offset"));
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sniffer_egg.hatch")), SoundSource.PLAYERS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sniffer_egg.crack")), SoundSource.PLAYERS, 1, 1, false);
			}
		}
		itemstack.shrink(1);
		BlockPos pos = BlockPos.containing(x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ());
		for (int index0 = 0; index0 < count; index0++) {
			double fr = rrr + Mth.nextDouble(RandomSource.create(), -1 / ooo, ooo * 0.05);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_CHICKEN.get().spawn(_level, pos, MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn instanceof OceanizedChickenEntity chicken) {
					chicken.setYRot(world.getRandom().nextFloat() * 360F);
					AttributeInstance max_h = chicken.getAttribute(Attributes.MAX_HEALTH);
					AttributeInstance atk = chicken.getAttribute(Attributes.ATTACK_DAMAGE);
					if (max_h != null) max_h.setBaseValue(max_h.getBaseValue() * fr);
					if (atk != null) atk.setBaseValue(atk.getBaseValue() * fr);
					chicken.setHealth(chicken.getMaxHealth());
					SynchedEntityData data = chicken.getEntityData();
					if (data != null) {
						data.set(OceanizedChickenEntity.DATA_EGG_RATE, (int) (fr * 1000));
						data.set(OceanizedChickenEntity.DATA_EGG_OFFSET, (int) ooo);
						data.set(OceanizedChickenEntity.DATA_IS_CHILD, true);
					}
				}
			}
			//CaerulaArborMod.LOGGER.info(("Summon chicken with rate: " + new java.text.DecimalFormat("##.##").format(fr) + " and with offset: " + new java.text.DecimalFormat("##.##").format(ooo)));
		}
		return InteractionResult.SUCCESS;
	}
}
