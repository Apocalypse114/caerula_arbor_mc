
package net.apocalypse.careunite.item;

import net.apocalypse.careunite.client.model.Modelinquisition_armor;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.client.model.Modelinquisition_suit;

import java.util.function.Consumer;
import java.util.List;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import java.util.UUID;
import com.google.common.collect.HashMultimap;

public abstract class InquisitionSuitItem extends ArmorItem {
	//public Modelinquisition_suit cache = null;

	public InquisitionSuitItem(ArmorItem.Type type, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(ArmorItem.Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 27;
			}

			@Override
			public int getDefenseForType(ArmorItem.Type type) {
				return new int[]{3, 5, 7, 3}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_CHAIN;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.LEATHER), new ItemStack(Items.AMETHYST_SHARD), new ItemStack(Items.IRON_INGOT), new ItemStack(CaerulaArborModItems.OCEAN_PHLOEM.get()));
			}

			@Override
			public String getName() {
				return "inquisition_suit";
			}

			@Override
			public float getToughness() {
				return 0.25f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.12f;
			}
		}, type, properties);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.inquisition_suit_chestplate.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.inquisition_suit_chestplate.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.inquisition_suit_chestplate.description_2"));
		list.add(Component.translatable("item.caerula_arbor_reunite.inquisition_suit_chestplate.description_3"));
	}

	@Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(slot.toString().hashCode(), 0);
        String name = "inquisition_attribute_modifier";
        if (slot == this.getEquipmentSlot()){
            map = HashMultimap.create(map);
            map.put(CaerulaArborModAttributes.MISSRATE.get(),
                    new AttributeModifier(uuid, name , 3f, AttributeModifier.Operation.ADDITION));
            map.put(CaerulaArborModAttributes.SANITY_RESISTANCE.get(),
                    new AttributeModifier(uuid, name , 5f, AttributeModifier.Operation.ADDITION));
        }
        return map;
    }


	public static class Chestplate extends InquisitionSuitItem {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelinquisition_suit cache = new Modelinquisition_suit(Minecraft.getInstance().getEntityModels().bakeLayer(Modelinquisition_suit.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(defaultModel,
							cache.nope, cache.Body, cache.RightArm2, cache.LeftArm2, cache.nope, cache.nope,
							living);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/inqusiition_suit.png";
		}
	}

	public static class Leggings extends InquisitionSuitItem {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelinquisition_suit cache = new Modelinquisition_suit(Minecraft.getInstance().getEntityModels().bakeLayer(Modelinquisition_suit.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(defaultModel,
							cache.nope, cache.nope,cache.nope, cache.nope, cache.RightLeg, cache.LeftLeg,
							living);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/inquisition_suit_leg.png";
		}
	}

	public static class Boots extends InquisitionSuitItem {
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
					Modelinquisition_suit cache = new Modelinquisition_suit(Minecraft.getInstance().getEntityModels().bakeLayer(Modelinquisition_suit.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(defaultModel,
							cache.nope, cache.nope,cache.nope, cache.nope, cache.RightBoots, cache.LeftBoots,
							living);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/inqusiition_suit.png";
		}
	}
}
