package com.fishy.fishyBusiness.block;

import com.fishy.fishyBusiness.FishyBusiness;
import com.fishy.fishyBusiness.block.custom.BlueweedBlock;
import com.fishy.fishyBusiness.block.custom.DuckweedBlock;
import com.fishy.fishyBusiness.block.custom.MarkerBlock;
import com.fishy.fishyBusiness.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(FishyBusiness.MOD_ID);

    public static final DeferredBlock<Block> DUCKWEED_BLOCK = registerBlock("duckweed_block",
            () -> new DuckweedBlock(
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.LILY_PAD).noOcclusion().pushReaction(PushReaction.DESTROY).noCollission()));
    public static final DeferredBlock<Block> REDWEED_BLOCK = registerBlock("redweed_block",
            () -> new DuckweedBlock(
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.LILY_PAD).noOcclusion().pushReaction(PushReaction.DESTROY).noCollission()));
    public static final DeferredBlock<Block> BLUEWEED_BLOCK = registerBlock("blueweed_block",
            () -> new BlueweedBlock(
                    BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instabreak().sound(SoundType.LILY_PAD).noOcclusion().pushReaction(PushReaction.DESTROY).noCollission()));

    public static final  DeferredBlock<Block> MARKER_BLOCK = registerBlock("marker_block",
            () -> new MarkerBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission().noLootTable().replaceable().noTerrainParticles().randomTicks()));




    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }

    private static <T extends  Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        if (name.equals("duckweed_block") || name.equals("redweed_block") || name.equals("blueweed_block")){
            registerBlockItemPlaceableOnWater(name, toReturn);
        }
        else {
            registerBlockItem(name, toReturn);
        }

        return toReturn;
    }


    private static <T extends Block> void  registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    private static <T extends Block> void  registerBlockItemPlaceableOnWater(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new PlaceOnWaterBlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
