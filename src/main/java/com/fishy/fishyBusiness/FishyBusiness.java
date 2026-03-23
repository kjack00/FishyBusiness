package com.fishy.fishyBusiness;

import com.fishy.fishyBusiness.block.ModBlocks;
import com.fishy.fishyBusiness.block.custom.entity.ModBlockEntities;
import com.fishy.fishyBusiness.block.custom.entity.renderer.MarkerBER;
import com.fishy.fishyBusiness.item.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FishyBusiness.MOD_ID)
public class FishyBusiness {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "fishybusiness";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FishyBusiness(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);


        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);


    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS){
            event.accept(ModBlocks.DUCKWEED_BLOCK);
            event.accept(ModBlocks.REDWEED_BLOCK);
            event.accept(ModBlocks.BLUEWEED_BLOCK);
            event.accept(ModBlocks.MARKER_BLOCK);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = FishyBusiness.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(
                        ModBlocks.DUCKWEED_BLOCK.get(),
                        RenderType.cutout()
                );
                ItemBlockRenderTypes.setRenderLayer(
                        ModBlocks.REDWEED_BLOCK.get(),
                        RenderType.cutout()
                );
                ItemBlockRenderTypes.setRenderLayer(
                        ModBlocks.BLUEWEED_BLOCK.get(),
                        RenderType.cutout()
                );
            });

        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.MARKER_BE.get(), MarkerBER::new);
        }

    }
}
