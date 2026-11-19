package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.InteractionHand;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import net.apocalypse.careunite.init.CaerulaArborModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class TransporterUseProcedure {
	public static final TagKey<EntityType<?>> HOMO_SAPIENS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:homo_sapiens"));
    public static final String emptyNameHolder = "apocata";
    public static final String TAG_NAME = "name";
    public static final String TAG_DATA = "data";
    
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		return;
//		ItemStack item = event.getItemStack();
//        InteractionHand hand = event.getHand();
//        Player player = event.getEntity();
//        Entity entity = event.getTarget();
//        if(entity == null) return;
//        if(player == null) return;
//        if(hand == InteractionHand.MAIN_HAND) {
//            if (event.getSide() == LogicalSide.CLIENT) return;
//            if (entity instanceof LivingEntity living && item.is(CaerulaArborModItems.PERSONNEL_TRANSPORTER.get())) {
//                //interact(item, player, living, hand);
//            }
//        }
	}

	public static void execute(Entity entity, boolean clientside) {
		return;
	}
}
