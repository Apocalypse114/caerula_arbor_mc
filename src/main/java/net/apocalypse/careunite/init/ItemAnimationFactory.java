package net.apocalypse.careunite.init;

import software.bernie.geckolib.animatable.GeoItem;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.item.ItemStack;

import net.apocalypse.careunite.item.WavecleaverItem;
import net.apocalypse.careunite.item.UninishedBeautyItem;
import net.apocalypse.careunite.item.PhloemBowItem;
import net.apocalypse.careunite.item.MartusBookItem;
import net.apocalypse.careunite.item.LegendarySpearItem;
import net.apocalypse.careunite.item.HighmoreScytheItem;
import net.apocalypse.careunite.item.CircularSawItem;

@Mod.EventBusSubscriber
public class ItemAnimationFactory {
	@SubscribeEvent
	public static void animatedItems(TickEvent.PlayerTickEvent event) {
		String animation = "";
		ItemStack mainhandItem = event.player.getMainHandItem().copy();
		ItemStack offhandItem = event.player.getOffhandItem().copy();
		if (event.phase == TickEvent.Phase.START && (mainhandItem.getItem() instanceof GeoItem || offhandItem.getItem() instanceof GeoItem)) {
			if (mainhandItem.getItem() instanceof PhloemBowItem animatable) {
				animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((PhloemBowItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (offhandItem.getItem() instanceof PhloemBowItem animatable) {
				animation = offhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((PhloemBowItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (mainhandItem.getItem() instanceof LegendarySpearItem animatable) {
				animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((LegendarySpearItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (offhandItem.getItem() instanceof LegendarySpearItem animatable) {
				animation = offhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((LegendarySpearItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (mainhandItem.getItem() instanceof WavecleaverItem animatable) {
				animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((WavecleaverItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (offhandItem.getItem() instanceof WavecleaverItem animatable) {
				animation = offhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((WavecleaverItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (mainhandItem.getItem() instanceof HighmoreScytheItem animatable) {
				animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((HighmoreScytheItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (offhandItem.getItem() instanceof HighmoreScytheItem animatable) {
				animation = offhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((HighmoreScytheItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (mainhandItem.getItem() instanceof CircularSawItem animatable) {
				animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((CircularSawItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (offhandItem.getItem() instanceof CircularSawItem animatable) {
				animation = offhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((CircularSawItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (mainhandItem.getItem() instanceof UninishedBeautyItem animatable) {
				animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((UninishedBeautyItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (offhandItem.getItem() instanceof UninishedBeautyItem animatable) {
				animation = offhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((UninishedBeautyItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (mainhandItem.getItem() instanceof MartusBookItem animatable) {
				animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((MartusBookItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
					}
				}
			}
			if (offhandItem.getItem() instanceof MartusBookItem animatable) {
				animation = offhandItem.getOrCreateTag().getString("geckoAnim");
				if (!animation.isEmpty()) {
					event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
					if (event.player.level().isClientSide()) {
						((MartusBookItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
					}
				}
			}
		}
	}
}
