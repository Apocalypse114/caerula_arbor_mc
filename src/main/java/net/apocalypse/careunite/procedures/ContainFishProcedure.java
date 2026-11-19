package net.apocalypse.careunite.procedures;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.SliderFishEntity;
import net.apocalypse.careunite.entity.RunFishEntity;
import net.apocalypse.careunite.entity.FloaterProkaryoteEntity;
import net.apocalypse.careunite.entity.CollectorProkaryoteEntity;
import net.apocalypse.careunite.entity.ChiselerFishEntity;
import net.apocalypse.careunite.entity.BoneFishEntity;

public class ContainFishProcedure {
	public static InteractionResult execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		boolean given = false;
		if ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(Items.WATER_BUCKET) : false) {
			if (entity instanceof RunFishEntity) {
				if (sourceentity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.BUCKET_RUNFISH.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				given = true;
			} else if (entity instanceof SliderFishEntity) {
				if (sourceentity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.BUCKET_SLIDER.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				given = true;
			} else if (entity instanceof ChiselerFishEntity) {
				if (sourceentity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.BUCKET_CHISELER.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				given = true;
			} else if (entity instanceof FloaterProkaryoteEntity) {
				if (sourceentity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.BUCKET_FLOATER.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				given = true;
			} else if (entity instanceof BoneFishEntity) {
				if (sourceentity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.BUCKET_BONEFISH.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				given = true;
			} else if (entity instanceof CollectorProkaryoteEntity) {
				if (sourceentity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.BUCKET_COLLECTOR.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				given = true;
			}
			if (given) {
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(Items.BUCKET);
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
				}
				if (!entity.level().isClientSide())
					entity.discard();
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.FAIL;
	}
}
