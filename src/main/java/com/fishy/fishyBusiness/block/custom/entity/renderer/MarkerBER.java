package com.fishy.fishyBusiness.block.custom.entity.renderer;

import com.fishy.fishyBusiness.block.custom.MarkerBlock;
import com.fishy.fishyBusiness.block.custom.entity.MarkerBlockEntity;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.Map;


public class MarkerBER implements BlockEntityRenderer<MarkerBlockEntity> {

    public MarkerBER(BlockEntityRendererProvider.Context context) {
    }

    float scale = 1.0f;
    float yOffset = 0.0001f;

    @Override
    public void render(@NotNull MarkerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ResourceLocation texture = blockEntity.getTextureLocation();
        VertexConsumer builder = bufferSource.getBuffer(RenderType.entityTranslucent(texture));
        if(blockEntity.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACE) == AttachFace.FLOOR) {
            yOffset = 0.0001f;
            drawQuad(builder, poseStack, 0f, 0 + yOffset, 0f, 1 * scale, 0 * scale + yOffset , 1 * scale, 0f, 0f, 1f, 1f, packedLight,1);
        } else if (blockEntity.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACE) == AttachFace.CEILING) {
            yOffset = 0.9999f;
            drawQuad(builder, poseStack, 0f, 0 + yOffset, 0f, 1 * scale, 0 * scale + yOffset , 1 * scale, 0f, 0f, 1f, 1f, packedLight,1);
        } else if (blockEntity.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACE) == AttachFace.WALL) {
            yOffset = 0f;
            if(blockEntity.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACING) == Direction.NORTH ||
            blockEntity.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACING) == Direction.UP ||
            blockEntity.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACING) == Direction.DOWN){

                drawQuad(builder, poseStack, 0f, 0 + yOffset, 0.9999f, 1 * scale, 1 * scale + yOffset , 0.9999f * scale, 0f, 0f, 1f, 1f, packedLight,1);
            }else if(blockEntity.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACING) == Direction.EAST){

                drawVertex(builder, poseStack, 0.0001f, 0f, 0f, 0f, 0f, packedLight, 1, 1, 1, 1);
                drawVertex(builder, poseStack, 0.0001f, 1f, 0f, 0f, 1f, packedLight, 1, 1, 1, 1);
                drawVertex(builder, poseStack, 0.0001f, 1f, 1f, 1f, 1f, packedLight, 1, 1, 1, 1);
                drawVertex(builder, poseStack, 0.0001f, 0f, 1f, 1f, 0f, packedLight, 1, 1, 1, 1); //i am lazy
            }else if(blockEntity.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACING) == Direction.SOUTH){

                drawQuad(builder, poseStack, 0f, 0 + yOffset, 0.0001f, 1 * scale, 1 * scale + yOffset , 0.0001f * scale, 0f, 0f, 1f, 1f, packedLight,1);
            }else if(blockEntity.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACING) == Direction.WEST){


                drawVertex(builder, poseStack, 0.9999f, 0f, 0f, 0f, 0f, packedLight, 1, 1, 1, 1);
                drawVertex(builder, poseStack, 0.9999f, 0f, 1f, 1f, 0f, packedLight, 1, 1, 1, 1);
                drawVertex(builder, poseStack, 0.9999f, 1f, 1f, 1f, 1f, packedLight, 1, 1, 1, 1);
                drawVertex(builder, poseStack, 0.9999f, 1f, 0f, 0f, 1f, packedLight, 1, 1, 1, 1); //i am lazy here too
            }
        }


    }

    private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, float red, float green, float blue, float alpha){
        builder.addVertex(poseStack.last().pose(), x, y,z)
                .setColor(red, green, blue, alpha)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(poseStack.last(), 0, 1, 0);
    }

    private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1 , float u0, float v0, float u1, float v1, int packedLight, int color){
        drawVertex(builder, poseStack, x0, y0 , z0, u0, v0, packedLight, 1, 1, 1, 1);
        drawVertex(builder, poseStack, x0, y1 , z1, u0, v1, packedLight, 1, 1, 1, 1);
        drawVertex(builder, poseStack, x1, y1 , z1, u1, v1, packedLight, 1, 1, 1, 1);
        drawVertex(builder, poseStack, x1, y0 , z0, u1, v0, packedLight, 1, 1, 1, 1);

    }



}
