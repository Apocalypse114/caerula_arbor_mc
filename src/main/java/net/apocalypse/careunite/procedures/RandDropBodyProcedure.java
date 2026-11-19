package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class RandDropBodyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		ItemStack mainHandItem = ItemStack.EMPTY;
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			mainHandItem = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
			if (mainHandItem.getItem() instanceof AxeItem || mainHandItem.is(ItemTags.create(new ResourceLocation("minecraft:swords"))) || mainHandItem.getItem() instanceof SwordItem
					|| mainHandItem.is(ItemTags.create(new ResourceLocation("minecraft:axes")))) {
				if (Math.random() < 0.33) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.COLLECTOR_MEAT.get()));
						entityToSpawn.setPickUpDelay(10);
						_level.addFreshEntity(entityToSpawn);
					}
				}
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("forge:tools/knives")))
					|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("forge:tools/knives")))) {
				if (Math.random() < 0.67) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.COLLECTOR_MEAT.get()));
						entityToSpawn.setPickUpDelay(10);
						_level.addFreshEntity(entityToSpawn);
					}
				}
			}
		}
	}
}
