package com.fishy.fishyBusiness.item;

import com.fishy.fishyBusiness.FishyBusiness;
import com.fishy.fishyBusiness.item.custom.MarkerItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(FishyBusiness.MOD_ID);

    public static final DeferredItem<Item> MARKER = ITEMS.registerItem("marker",
            MarkerItem::new, new Item.Properties());

public static void register(IEventBus eventBus){
    ITEMS.register(eventBus);
}



}
