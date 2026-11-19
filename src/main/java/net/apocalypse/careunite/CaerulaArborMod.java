package net.apocalypse.careunite;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;

import net.apocalypse.careunite.init.CaerulaArborModVillagerProfessions;
import net.apocalypse.careunite.init.CaerulaArborModTabs;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.apocalypse.careunite.init.CaerulaArborModPotions;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModPaintings;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModMenus;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModEnchantments;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.init.CaerulaArborModBlockEntities;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.AbstractMap;

@Mod("caerula_arbor_reunite")
public class CaerulaArborMod {
	public static final Logger LOGGER = LogManager.getLogger(CaerulaArborMod.class);
	public static final String MODID = "caerula_arbor_reunite";

	public CaerulaArborMod() {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		MinecraftForge.EVENT_BUS.register(this);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		CaerulaArborModSounds.REGISTRY.register(bus);
		CaerulaArborModBlocks.REGISTRY.register(bus);
		CaerulaArborModBlockEntities.REGISTRY.register(bus);
		CaerulaArborModItems.REGISTRY.register(bus);
		CaerulaArborModEntities.REGISTRY.register(bus);
		CaerulaArborModEnchantments.REGISTRY.register(bus);
		CaerulaArborModTabs.REGISTRY.register(bus);
		CaerulaArborModMobEffects.REGISTRY.register(bus);
		CaerulaArborModPotions.REGISTRY.register(bus);
		CaerulaArborModPaintings.REGISTRY.register(bus);
		CaerulaArborModParticleTypes.REGISTRY.register(bus);
		CaerulaArborModVillagerProfessions.PROFESSIONS.register(bus);
		CaerulaArborModMenus.REGISTRY.register(bus);
		CaerulaArborModAttributes.REGISTRY.register(bus);
		// Start of user code block mod init
		//CASoundEvents.REGISTRY.register(bus);
		// End of user code block mod init
	}

	// Start of user code block mod methods
	// End of user code block mod methods
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}

	private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
	}

	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
			workQueue.forEach(work -> {
				work.setValue(work.getValue() - 1);
				if (work.getValue() == 0)
					actions.add(work);
			});
			actions.forEach(e -> e.getKey().run());
			workQueue.removeAll(actions);
		}
	}
}
