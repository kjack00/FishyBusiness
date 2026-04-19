package com.fishy.fishyBusiness.components;

import com.fishy.fishyBusiness.FishyBusiness;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE,FishyBusiness.MOD_ID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COLOR =
            register("color", integerBuilder -> integerBuilder.persistent(Codec.INT));


    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name, ()-> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus iEventBus){
        DATA_COMPONENT_TYPES.register(iEventBus);
    }
}
