
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
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.client.model.Modelmaster_alchemist;
import net.apocalypse.careunite.procedures.IsAlchemistSuitProcedure;

import java.util.function.Consumer;
import java.util.List;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import java.util.UUID;
import com.google.common.collect.HashMultimap;
import net.minecraft.world.entity.player.Player;
import com.google.common.collect.Iterables;

public abstract class MasterAlchemistItem extends ArmorItem {
	public MasterAlchemistItem(ArmorItem.Type type, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(ArmorItem.Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 15;
			}

			@Override
			public int getDefenseForType(ArmorItem.Type type) {
				return new int[]{2, 5, 6, 3}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 24;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(CaerulaArborModItems.OCEAN_PHLOEM.get()), new ItemStack(Items.LEATHER));
			}

			@Override
			public String getName() {
				return "master_alchemist";
			}

			@Override
			public float getToughness() {
				return 0f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.07f;
			}
		}, type, properties);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.master_alchemist_helmet.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.master_alchemist_helmet.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.master_alchemist_helmet.description_2"));
		list.add(Component.translatable("item.caerula_arbor_reunite.master_alchemist_helmet.description_3"));
	}

	@Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(slot.toString().hashCode(), 0);
        String name = "caerula_arbor_reunite_attribute_modifier";
        if (slot == this.getEquipmentSlot()){
            map = HashMultimap .create(map);
            map.put(CaerulaArborModAttributes.MISSRATE.get(),
                    new AttributeModifier(uuid, name , 1.5f, AttributeModifier.Operation.ADDITION));
            map.put(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(),
                    new AttributeModifier(uuid, name , 8f, AttributeModifier.Operation.ADDITION));
        }
        return map;
    }


	public static class Helmet extends MasterAlchemistItem {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelmaster_alchemist cache = new Modelmaster_alchemist(Minecraft.getInstance().getEntityModels().bakeLayer(Modelmaster_alchemist.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(
							defaultModel, 
							cache.nope, cache.nope, cache.nope, cache.nope, cache.nope, cache.nope, cache.head, 
							living
					);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/master_alchemist_armor.png";
		}
	}

	public static class Chestplate extends MasterAlchemistItem {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelmaster_alchemist cache = new Modelmaster_alchemist(Minecraft.getInstance().getEntityModels().bakeLayer(Modelmaster_alchemist.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(
							defaultModel,cache.nope, cache.body, cache.armRight, cache.armLeft,
							cache.nope, cache.nope, living
					);
				}
			});
		}

		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				IsAlchemistSuitProcedure.execute(entity);
			}
		}


		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/master_alchemist_armor.png";
		}
	}

	public static class Leggings extends MasterAlchemistItem {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelmaster_alchemist cache = new Modelmaster_alchemist(Minecraft.getInstance().getEntityModels().bakeLayer(Modelmaster_alchemist.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(
							defaultModel,cache.nope, cache.nope,cache.nope, cache.nope,
							cache.legLeft, cache.legRight, living
					);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/master_alchemist_armor.png";
		}
	}

	public static class Boots extends MasterAlchemistItem {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					Modelmaster_alchemist cache = new Modelmaster_alchemist(Minecraft.getInstance().getEntityModels().bakeLayer(Modelmaster_alchemist.LAYER_LOCATION));
					return SealeatherChitinItem.buildArmorModel(
							defaultModel,cache.nope, cache.nope,cache.nope, cache.nope,
							cache.bootLeft, cache.bootRight, living
					);
				}
			});
		}

		public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer){
		    return true;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "caerula_arbor_reunite:textures/entities/master_alchemist_armor.png";
		}
	}
}
