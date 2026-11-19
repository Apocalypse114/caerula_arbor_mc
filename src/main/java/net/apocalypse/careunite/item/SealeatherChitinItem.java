
package net.apocalypse.careunite.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.Minecraft;

import net.minecraft.world.level.Level;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.apocalypse.careunite.client.model.Modelsealeather_chitin_armor;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Iterables;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;
import java.util.Map;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class SealeatherChitinItem extends ArmorItem {
	public SealeatherChitinItem(ArmorItem.Type type, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(ArmorItem.Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 25;
			}

			@Override
			public int getDefenseForType(ArmorItem.Type type) {
				return new int[]{3, 6, 7, 3}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 15;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_CHAIN;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(CaerulaArborModItems.OCEAN_CHITIN.get()), new ItemStack(CaerulaArborModItems.OCEAN_PHLOEM.get()));
			}

			@Override
			public String getName() {
				return "sealeather_chitin";
			}

			@Override
			public float getToughness() {
				return 0.5f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, type, properties);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.sealeather_chitin.desc"));
	}


	public static class Helmet extends SealeatherChitinItem {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelsealeather_chitin_armor cache = new Modelsealeather_chitin_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelsealeather_chitin_armor.LAYER_LOCATION));
					return buildArmorModel(
							defaultModel, 
							cache.nope, cache.nope, cache.nope, cache.nope, cache.nope, cache.nope, cache.helmet, 
							living
					);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/chitin_pholem_helm.png";
		}
	}

	@Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(slot.toString().hashCode(), 0);
        String name = "caerula_arbor_reunite_attribute_modifier";
        if (slot == this.getEquipmentSlot()){
            map = HashMultimap.create(map);
            map.put(CaerulaArborModAttributes.MISSRATE.get(),
                    new AttributeModifier(uuid, name , 4f, AttributeModifier.Operation.ADDITION));
            map.put(CaerulaArborModAttributes.SANITY_RESISTANCE.get(),
                    new AttributeModifier(uuid, name , 8f, AttributeModifier.Operation.ADDITION));
        }
        return map;
    }

	public static class Chestplate extends SealeatherChitinItem {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, level, list, flag);
			list.add(Component.translatable("item.caerula_arbor_reunite.sealeather_chitin_chestplate.descr"));
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelsealeather_chitin_armor cache = new Modelsealeather_chitin_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelsealeather_chitin_armor.LAYER_LOCATION));
					return buildArmorModel(
							defaultModel,cache.nope, cache.chestplt, cache.chestarmR, cache.chesarmL,
							cache.nope, cache.nope, living
					);
				}
			});
		}

		@Override
    	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        	super.inventoryTick(itemstack, world, entity, slot, selected);
        	if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
        		if (player.hasEffect(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get())) return;
            	if (player.tickCount % 550 == 64){
                	player.addEffect(
                		new MobEffectInstance(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get(), 
                		400, 0, false, false)
                		);
            	}
        	}
        }

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/chitin_pholem.png";
		}
	}

	public static class Leggings extends SealeatherChitinItem {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelsealeather_chitin_armor cache = new Modelsealeather_chitin_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelsealeather_chitin_armor.LAYER_LOCATION));
					return buildArmorModel(
							defaultModel,cache.nope, cache.nope, cache.nope, cache.nope,
							cache.legL, cache.legR, living
					);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/chitin_pholem.png";
		}
	}

	public static class Boots extends SealeatherChitinItem {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties());
		}



		public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer){
		    return true;
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelsealeather_chitin_armor cache = new Modelsealeather_chitin_armor(Minecraft.getInstance().getEntityModels().bakeLayer(Modelsealeather_chitin_armor.LAYER_LOCATION));
					return buildArmorModel(
							defaultModel,cache.nope, cache.nope,cache.nope, cache.nope,
							cache.bootL, cache.bootR, living
					);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/chitin_pholem.png";
		}
	}

	//public Modelsealeather_chitin_armor cache = null;

	@OnlyIn(Dist.CLIENT)
	protected static HumanoidModel buildArmorModel(
	        HumanoidModel defaultModel,
	        ModelPart head, ModelPart body, ModelPart rightArm, ModelPart leftArm,
	        ModelPart rightLeg, ModelPart leftLeg, ModelPart hat,
	        LivingEntity living) {
	    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
	        Map.of(
	            "head",      head      != null ? head      : defaultModel.head,
	            "hat", 		  hat       != null ? hat       : defaultModel.hat,
	            "body",      body      != null ? body      : defaultModel.body,
	            "right_arm", rightArm  != null ? rightArm  : defaultModel.rightArm,
	            "left_arm",  leftArm   != null ? leftArm   : defaultModel.leftArm,
	            "right_leg", rightLeg  != null ? rightLeg  : defaultModel.rightLeg,
	            "left_leg",  leftLeg   != null ? leftLeg   : defaultModel.leftLeg
	        )
	    ));
	    armorModel.crouching = living.isShiftKeyDown();
	    armorModel.riding = defaultModel.riding;
	    armorModel.young = living.isBaby();
	    return armorModel;
	}
	protected static HumanoidModel buildArmorModel(
	        HumanoidModel defaultModel,
	        ModelPart head, ModelPart body, ModelPart rightArm, ModelPart leftArm,
	        ModelPart rightLeg, ModelPart leftLeg,
	        LivingEntity living) {
	    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
	        Map.of(
	            "head",      head      != null ? head      : defaultModel.head,
	            "hat", 		  defaultModel.hat,
	            "body",      body      != null ? body      : defaultModel.body,
	            "right_arm", rightArm  != null ? rightArm  : defaultModel.rightArm,
	            "left_arm",  leftArm   != null ? leftArm   : defaultModel.leftArm,
	            "right_leg", rightLeg  != null ? rightLeg  : defaultModel.rightLeg,
	            "left_leg",  leftLeg   != null ? leftLeg   : defaultModel.leftLeg
	        )
	    ));
	    armorModel.crouching = living.isShiftKeyDown();
	    armorModel.riding = defaultModel.riding;
	    armorModel.young = living.isBaby();
	    return armorModel;
	}
}
