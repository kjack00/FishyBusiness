package com.fishy.fishyBusiness.item.custom;

import com.fishy.fishyBusiness.block.ModBlocks;
import com.fishy.fishyBusiness.block.custom.MarkerBlock;
import com.fishy.fishyBusiness.block.custom.entity.MarkerBlockEntity;
import com.fishy.fishyBusiness.block.custom.entity.renderer.MarkerBER;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;

public class MarkerItem extends Item {

    public MarkerItem(Properties properties) {
        super(properties);
    }

    // Up/Down use x,z
    // N/S use x,y
    // E/W us z, y


    private static void drawLogic(int x,int y,int z, Direction face, int col, MarkerBER markerBER){
        if(face == Direction.UP || face == Direction.DOWN){
            markerBER.drawPx((int) x, (int) z, col);

        }else if(face == Direction.NORTH || face == Direction.SOUTH){
            markerBER.drawPx((int) x, (int) y, col);

        }else if(face == Direction.EAST || face == Direction.WEST){
            markerBER.drawPx((int) z, (int) y, col);

        }
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block block = level.getBlockState(context.getClickedPos()).getBlock();
        Player player = context.getPlayer();
        if(block.equals(ModBlocks.MARKER_BLOCK.get())) {

            double x = context.getClickLocation().x;
            double y = context.getClickLocation().y;
            double z = context.getClickLocation().z;
            x = (x - Math.floor(x))*16;
            y = (y - Math.floor(y))*16;
            z = (z - Math.floor(z))*16;

            Direction face = context.getClickedFace();


            MarkerBlockEntity markerBlockEntity = (MarkerBlockEntity) level.getBlockEntity(context.getClickedPos());
            if (Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(markerBlockEntity) instanceof MarkerBER markerBER){
                if(player.isCrouching()){
                    drawLogic((int) x, (int) y, (int) z, face, 0x00000000, markerBER);

                }else {
                    drawLogic((int) x, (int) y, (int) z, face, 0xFF00FF00, markerBER);
                }
                System.out.println(x + "," + z);
            }



        }
        return super.useOn(context);
    }
}
