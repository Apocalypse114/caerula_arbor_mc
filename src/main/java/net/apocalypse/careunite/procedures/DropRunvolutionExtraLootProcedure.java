package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class DropRunvolutionExtraLootProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		ItemStack drop = ItemStack.EMPTY;
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			if (world instanceof ServerLevel _level)
				_level.addFreshEntity(new ExperienceOrb(_level, x, (y + 2), z, Mth.nextInt(RandomSource.create(), 24, 32)));
			drop = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("bobsoriginiumdream:translationgrain"))).copy();
			drop.setCount(Mth.nextInt(RandomSource.create(), 3, 6));
			if (drop.getItem() == ItemStack.EMPTY.getItem()) {
				drop = new ItemStack(CaerulaArborModItems.REDSTONE_INGOT.get()).copy();
				drop.setCount(Mth.nextInt(RandomSource.create(), 2, 4));
			}
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, drop);
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
			drop = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("bobsoriginiumdream:translationtie"))).copy();
			if (drop.getItem() == ItemStack.EMPTY.getItem()) {
				drop = new ItemStack(CaerulaArborModItems.REDSTONE_INGOT.get()).copy();
			}
			drop.setCount(Mth.nextInt(RandomSource.create(), 2, 4));
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, drop);
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
			drop = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("bobsoriginiumdream:translationhuge"))).copy();
			if (drop.getItem() == ItemStack.EMPTY.getItem()) {
				drop = new ItemStack(CaerulaArborModItems.CHITIN_INGOT.get()).copy();
			}
			drop.setCount(Mth.nextInt(RandomSource.create(), 1, 2));
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, drop);
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
			drop = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("bobsoriginiumdream:oriparticle_1"))).copy();
			if (drop.getItem() == ItemStack.EMPTY.getItem()) {
				drop = new ItemStack(CaerulaArborModItems.REDSTONE_INGOT.get()).copy();
			}
			drop.setCount(Mth.nextInt(RandomSource.create(), 1, 4));
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, drop);
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
			if (Math.random() < 0.33) {
				drop = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("bobsoriginiumdream:orismithingtemplate"))).copy();
				if (drop.getItem() == ItemStack.EMPTY.getItem()) {
					drop = new ItemStack(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE).copy();
				}
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, drop);
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
			}
		}
	}
}
