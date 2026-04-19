package com.fishy.fishyBusiness.client;

import com.fishy.fishyBusiness.item.ModItems;
import com.fishy.fishyBusiness.item.custom.MarkerItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ColorHandlers {

    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event){
        event.register(MarkerItem::getItemColor, ModItems.MARKER);
    }


}
