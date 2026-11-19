package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class OpenTraderBoxProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
		double mmm = 0;
		if (Math.random() < 0.15) {
			mmm = Math.random() * 1.6;
			if (mmm < 0.1) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.CAERULA_OFFSPRING.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else if (mmm < 0.3) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.TOMBSTONE_LIVING.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else if (mmm < 0.5) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.TOMBSTONE_STULTIFERA_SMALL.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else if (mmm < 0.7) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.TOMBSTONE_UNDERTIDE_SMALL.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else if (mmm < 0.9) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.SPIKE_CHEST.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else if (mmm < 1.1) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.PROMOTED_SQUID.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else if (mmm < 1.3) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.PROMOTED_AXOLOTL.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else if (mmm < 1.45) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.SONS.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			} else if (mmm < 1.6) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.FAKE_OFFSPRING.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			}
		} else {
			for (int index0 = 0; index0 < 3; index0++) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1), z,
							new ItemStack((ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:trader_goods"))).getRandomElement(RandomSource.create()).orElseGet(() -> Items.AIR))));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
			}
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.shulker_box.open")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.shulker_box.open")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
			}
		}
		itemstack.shrink(1);
	}
}
