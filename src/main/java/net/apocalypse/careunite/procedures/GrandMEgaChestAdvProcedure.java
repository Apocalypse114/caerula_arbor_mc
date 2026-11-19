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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.ScreamChestFishEntity;

public class GrandMEgaChestAdvProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		ItemStack mainHandItem = ItemStack.EMPTY;
		if ((entity instanceof ScreamChestFishEntity _datEntI ? _datEntI.getEntityData().get(ScreamChestFishEntity.DATA_SCREAM_TICK) : 0) > 0) {
			if (sourceentity instanceof ServerPlayer _player) {
				Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:i_scream"));
				AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
				if (!_ap.isDone()) {
					for (String criteria : _ap.getRemainingCriteria())
						_player.getAdvancements().award(_adv, criteria);
				}
			}
		}
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			mainHandItem = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
			if (mainHandItem.getItem() instanceof AxeItem || mainHandItem.is(ItemTags.create(new ResourceLocation("minecraft:swords"))) || mainHandItem.getItem() instanceof SwordItem
					|| mainHandItem.is(ItemTags.create(new ResourceLocation("minecraft:axes")))) {
				if (Math.random() < 0.33) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()), new ItemStack(CaerulaArborModBlocks.CHEST_FISH_FOOD.get()));
						entityToSpawn.setPickUpDelay(10);
						_level.addFreshEntity(entityToSpawn);
					}
				}
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("forge:tools/knives")))
					|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("forge:tools/knives")))) {
				if (Math.random() < 0.67) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()), new ItemStack(CaerulaArborModBlocks.CHEST_FISH_FOOD.get()));
						entityToSpawn.setPickUpDelay(10);
						_level.addFreshEntity(entityToSpawn);
					}
				}
			}
		}
	}
}
