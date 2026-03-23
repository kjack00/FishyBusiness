package com.fishy.fishyBusiness.block.custom.entity.renderer;

import com.fishy.fishyBusiness.block.custom.entity.MarkerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.NotNull;

public class MarkerBER implements BlockEntityRenderer<MarkerBlockEntity> {

    public MarkerBER(BlockEntityRendererProvider.Context context) {
    }



    @Override
    public void render(@NotNull MarkerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ResourceLocation andesiteTextureLocation = ResourceLocation.withDefaultNamespace("block/andesite");
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(andesiteTextureLocation);
        VertexConsumer builder = bufferSource.getBuffer(RenderType.cutout());

        drawQuad(builder, poseStack, 0f, 0f , 0f, 1f, 1f , 1f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), packedLight,1);
        System.out.println("Marker Block Entity Render Called: " + blockEntity);

        //drawVertex(builder, poseStack, 1f, 0 , 1f, sprite.getU0(), sprite.getV0(), packedLight, 0, 255, 0, 1);
        //drawVertex(builder, poseStack, 1f, 0 , -1f, sprite.getU0(), sprite.getV0(), packedLight, 0, 255, 0, 1);
        //drawVertex(builder, poseStack, -1f, 0 , 1f, sprite.getU0(), sprite.getV0(), packedLight, 0, 255, 0, 1);
       // drawVertex(builder, poseStack, -1f, 0 , -1f, sprite.getU0(), sprite.getV0(), packedLight, 0, 255, 0, 1);
    }

    private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, float red, float green, float blue, float alpha){
        builder.addVertex(poseStack.last().pose(), x, y,z)
                .setColor(red, green, blue, alpha)
                .setUv(u, v)
                .setLight(packedLight)
                .setNormal(0, 1, 0);
    }

    private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1 , float u0, float v0, float u1, float v1, int packedLight, int color){
        drawVertex(builder, poseStack, x0, y0 , z0, u0, v0, packedLight, 0, 1, 0, 1);
        drawVertex(builder, poseStack, x0, y1 , z1, u0, v1, packedLight, 0, 1, 0, 1);
        drawVertex(builder, poseStack, x1, y1 , z1, u1, v1, packedLight, 0, 1, 0, 1);
        drawVertex(builder, poseStack, x1, y0 , z0, u1, v0, packedLight, 0, 1, 0, 1);

    }



}
