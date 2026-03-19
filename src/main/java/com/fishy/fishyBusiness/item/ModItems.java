package com.fishy.fishyBusiness.item;

import com.fishy.fishyBusiness.FishyBusiness;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(FishyBusiness.MOD_ID);


public static void register(IEventBus eventBus){
    ITEMS.register(eventBus);
}



}
