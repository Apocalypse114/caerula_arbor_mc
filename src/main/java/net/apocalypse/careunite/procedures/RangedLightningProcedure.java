package net.apocalypse.careunite.procedures;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.function.Supplier;
import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber(value = {Dist.CLIENT})
public class RangedLightningProcedure {
	@SubscribeEvent
	public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
		CaerulaArborMod.PACKET_HANDLER.sendToServer(new RangedLightningMessage());
		execute(event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RangedLightningMessage {
		public RangedLightningMessage() {
		}

		public RangedLightningMessage(FriendlyByteBuf buffer) {
		}

		public static void buffer(RangedLightningMessage message, FriendlyByteBuf buffer) {
		}

		public static void handler(RangedLightningMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getSender().level().hasChunkAt(context.getSender().blockPosition()))
					return;
				execute(context.getSender().level(), context.getSender().getX(), context.getSender().getY(), context.getSender().getZ(), context.getSender());
			});
			context.setPacketHandled(true);
		}

		@SubscribeEvent
		public static void registerMessage(FMLCommonSetupEvent event) {
			CaerulaArborMod.addNetworkMessage(RangedLightningMessage.class, RangedLightningMessage::buffer, RangedLightningMessage::new, RangedLightningMessage::handler);
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.APOCATA_SWORD.get()) : false) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof LightningBolt) {
						continue;
					}
					if ((entityiterator.getDisplayName().getString()).equals("display")) {
						continue;
					}
					if (!(entity == entityiterator)) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:inv_killer")))), 114514);
					}
				}
			}
		}
	}
}
