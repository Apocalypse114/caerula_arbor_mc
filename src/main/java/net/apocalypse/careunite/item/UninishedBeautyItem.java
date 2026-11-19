
package net.apocalypse.careunite.item;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoItem;

import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

import net.apocalypse.careunite.procedures.SmashMobsProcedure;
import net.apocalypse.careunite.procedures.GainLessSpeedProcedure;
import net.apocalypse.careunite.procedures.CutManyWoodProcedure;
import net.apocalypse.careunite.item.renderer.UninishedBeautyItemRenderer;
import net.apocalypse.careunite.CaerulaTiers;

import java.util.function.Consumer;
import java.util.List;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.player.Player;

public class UninishedBeautyItem extends AxeItem implements GeoItem {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public String animationprocedure = "empty";

	public UninishedBeautyItem() {
		super(CaerulaTiers.AEGIR, 10, -3, new Item.Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
      return true;
   }

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			private final BlockEntityWithoutLevelRenderer renderer = new UninishedBeautyItemRenderer();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

	private PlayState idlePredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.unfinished_beautuy.idle"));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationState event) {
		if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
			if (!this.animationprocedure.equals(prevAnim))
				event.getController().forceAnimationReset();
			event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
			if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
				this.animationprocedure = "empty";
				event.getController().forceAnimationReset();
			}
		} else if (this.animationprocedure.equals("empty")) {
			prevAnim = "empty";
			return PlayState.STOP;
		}
		prevAnim = this.animationprocedure;
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		AnimationController procedureController = new AnimationController(this, "procedureController", 0, this::procedurePredicate);
		data.add(procedureController);
		AnimationController idleController = new AnimationController(this, "idleController", 0, this::idlePredicate);
		data.add(idleController);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
		return 2F;
	}

//	@Override
//	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
//		if (equipmentSlot == EquipmentSlot.MAINHAND) {
//			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
//			builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
//			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 15d, AttributeModifier.Operation.ADDITION));
//			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -3, AttributeModifier.Operation.ADDITION));
//			return builder.build();
//		}
//		return super.getDefaultAttributeModifiers(equipmentSlot);
//	}

	@Override
	public boolean isCorrectToolForDrops(BlockState state) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.unfinished_beauty.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.unfinished_beauty.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.unfinished_beauty.description_2"));
		list.add(Component.translatable("item.caerula_arbor_reunite.unfinished_beauty.description_3"));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		if (player == null) return InteractionResult.PASS;
		if (!player.isShiftKeyDown()) return super.useOn(context);
		return CutManyWoodProcedure.execute(context.getLevel(), 
			context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), 
			context.getLevel().getBlockState(context.getClickedPos()), 
			context.getPlayer(), context.getItemInHand());
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		SmashMobsProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceentity, itemstack);
		return retval;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			GainLessSpeedProcedure.execute(entity);
	}

	@Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }
}
