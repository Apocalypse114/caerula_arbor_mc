
package net.apocalypse.careunite.item;

import net.minecraftforge.registries.ForgeRegistries;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.client.model.Modelbatalion;

import java.util.function.Consumer;
import java.util.List;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import java.util.UUID;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import com.google.common.collect.HashMultimap;
import net.minecraft.ChatFormatting;

public abstract class BatalionArmorItem extends ArmorItem {
	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.batalion_armor_helmet.description_0").withStyle(ChatFormatting.GRAY));
		list.add(Component.translatable("item.caerula_arbor_reunite.batalion_armor_helmet.description_1").withStyle(ChatFormatting.GRAY));
	}

	@Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(slot.toString().hashCode(), 0);
        String name = "caerula_arbor_reunite_attribute_modifier";
        if (slot == this.getEquipmentSlot()){
            map = HashMultimap.create(map);
            map.put(CaerulaArborModAttributes.SANITY_RESISTANCE.get(),
                    new AttributeModifier(uuid, name , 9f, AttributeModifier.Operation.ADDITION));
        }
        return map;
    }

	public BatalionArmorItem(ArmorItem.Type type, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(ArmorItem.Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 24;
			}

			@Override
			public int getDefenseForType(ArmorItem.Type type) {
				return new int[]{3, 5, 7, 3}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 12;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(CaerulaArborModItems.OCEAN_PHLOEM.get()), new ItemStack(Items.IRON_INGOT));
			}

			@Override
			public String getName() {
				return "batalion_armor";
			}

			@Override
			public float getToughness() {
				return 0.25f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.1f;
			}
		}, type, properties);
	}

	public static class Helmet extends BatalionArmorItem {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelbatalion cache = new Modelbatalion(Minecraft.getInstance().getEntityModels().bakeLayer(Modelbatalion.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(
							defaultModel, 
							cache.nope, cache.nope, cache.nope, cache.nope, cache.nope, cache.nope, cache.Head, 
							living);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/batalion_armor.png";
		}
	}

	public static class Chestplate extends BatalionArmorItem {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelbatalion cache = new Modelbatalion(Minecraft.getInstance().getEntityModels().bakeLayer(Modelbatalion.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(
							defaultModel, 
							cache.nope, cache.Body, cache.Right_Arm, cache.Left_Arm, cache.nope, cache.nope, cache.nope, 
							living);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/batalion_armor.png";
		}
	}

	public static class Leggings extends BatalionArmorItem {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelbatalion cache = new Modelbatalion(Minecraft.getInstance().getEntityModels().bakeLayer(Modelbatalion.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(
							defaultModel, 
							cache.nope, cache.lower, cache.nope, cache.nope, cache.Right_Leg, cache.Left_Leg, cache.nope, 
							living);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/batalion_armor.png";
		}
	}

	public static class Boots extends BatalionArmorItem {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelbatalion cache = new Modelbatalion(Minecraft.getInstance().getEntityModels().bakeLayer(Modelbatalion.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(
							defaultModel, 
							cache.nope, cache.nope, cache.nope, cache.nope, cache.Righ_Foot, cache.Left_Foot, cache.nope, 
							living);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/batalion_armor.png";
		}
	}
}
