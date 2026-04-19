package com.fishy.fishyBusiness.item.custom;

import com.fishy.fishyBusiness.block.ModBlocks;
import com.fishy.fishyBusiness.block.custom.entity.MarkerBlockEntity;
import com.fishy.fishyBusiness.block.custom.entity.renderer.MarkerBER;
import com.fishy.fishyBusiness.components.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;

public class MarkerItem extends Item {

    public MarkerItem(Properties properties) {
        super(properties);
    }

    // Up/Down use x,z
    // N/S use x,y
    // E/W us z, y


    private static void drawLogic(int x,int y,int z, Direction face, int col, MarkerBlockEntity markerBlockEntity){
        if(face == Direction.UP || face == Direction.DOWN){
            markerBlockEntity.drawPx((int) x, (int) z, col);

        }else if(face == Direction.NORTH || face == Direction.SOUTH){
            markerBlockEntity.drawPx((int) x, (int) y, col);

        }else if(face == Direction.EAST || face == Direction.WEST){
            markerBlockEntity.drawPx((int) z, (int) y, col);

        }
    }

//    int prevX = 0;
//    int prevY = 0;
//    int prevZ = 0;
//    int prevCol ;
//    @Override
//    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
//       if(isSelected && entity instanceof Player player){
//           HitResult hitResult = player.pick(5.0f, 0.0f, false);
//           if(hitResult.getType() == HitResult.Type.BLOCK){
//               BlockHitResult blockHitResult = (BlockHitResult) hitResult;
//               BlockPos pos = blockHitResult.getBlockPos();
//               if (level.getBlockState(pos).is(ModBlocks.MARKER_BLOCK.get())){
//                   double x = hitResult.getLocation().x;
//                   double y = hitResult.getLocation().y;
//                   double z = hitResult.getLocation().z;
//                   x = (x - Math.floor(x))*16;
//                   y = (y - Math.floor(y))*16;
//                   z = (z - Math.floor(z))*16;
//
//
//
//
//                   MarkerBlockEntity markerBlockEntity = (MarkerBlockEntity) level.getBlockEntity(pos);
//                   int col = (int) markerBlockEntity.getPxCol((int)x, (int)y);
//                   Direction face = blockHitResult.getDirection();
//                   if(prevX != (int) x || prevY != (int) y || prevZ != (int) z) {
//                       System.out.println(face + "("+x +", " + z +")");
//                       drawLogic((int) x, (int) y, (int) z, face, col + 0x22FFFFFF, (MarkerBlockEntity) level.getBlockEntity(pos));
//                       drawLogic(prevX, prevY, prevZ, face, prevCol, (MarkerBlockEntity) level.getBlockEntity(pos));
//                   }
//                   //System.out.println(face + "("+x +", " + z +")");
//                   prevX = (int) x;
//                   prevY = (int) y;
//                   prevZ = (int) z;
//                   prevCol = col;
//               }
//           }
//       }
//        super.inventoryTick(stack, level, entity, slotId, isSelected);
//    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block block = level.getBlockState(context.getClickedPos()).getBlock();
        Player player = context.getPlayer();
        if(block.equals(ModBlocks.MARKER_BLOCK.get())) {
            int color = getMarkerColor(context.getItemInHand());


            double x = context.getClickLocation().x;
            double y = context.getClickLocation().y;
            double z = context.getClickLocation().z;
            x = (x - Math.floor(x))*16;
            y = (y - Math.floor(y))*16;
            z = (z - Math.floor(z))*16;

            Direction face = context.getClickedFace();


            MarkerBlockEntity markerBlockEntity = (MarkerBlockEntity) level.getBlockEntity(context.getClickedPos());
            if(player.isCrouching()){
                drawLogic((int) x, (int) y, (int) z, face, 0x00000000, markerBlockEntity);

            }else {
                drawLogic((int) x, (int) y, (int) z, face, color, markerBlockEntity);
            }
            //System.out.println(x + "," + z);


        }
        return super.useOn(context);
    }

    public static int getMarkerColor(ItemStack itemStack){
        if(itemStack.get(ModDataComponents.COLOR) != null) {
            int color = itemStack.get(ModDataComponents.COLOR);

            return FastColor.ARGB32.opaque(color);
        }
        return 0xFFFFFFFF;
    }

    public static void setMarkerColor(ItemStack itemStack, int color){

        itemStack.set(ModDataComponents.COLOR, color);
    }

    public static int getItemColor(ItemStack itemStack, int tintIndex) {
        if (tintIndex == 0) {
            int color = getMarkerColor(itemStack);
            int a = (color >> 24) & 0xFF;
            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = color & 0xFF;
            return (a << 24) | (b << 16) | (g << 8) | r;
        }
        return 0xFFFFFFFF;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        if(stack.get(ModDataComponents.COLOR) != null){
            tooltipComponents.add(Component.literal("Color: " + stack.get(ModDataComponents.COLOR)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
