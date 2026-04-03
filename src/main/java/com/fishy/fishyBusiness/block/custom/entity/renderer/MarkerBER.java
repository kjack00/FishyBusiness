package com.fishy.fishyBusiness.block.custom.entity.renderer;

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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class MarkerBER implements BlockEntityRenderer<MarkerBlockEntity> {

    public MarkerBER(BlockEntityRendererProvider.Context context) {
    }

    public NativeImage customTexture = new NativeImage(16, 16,true);
    public DynamicTexture dynamicTexture = new DynamicTexture(this.customTexture);


    public void drawPx(int x, int y, int col){
        this.customTexture.setPixelRGBA(x, y, col);
        this.dynamicTexture.upload();
    }

    public Map<Vec2, Integer> getPx(){
        Map<Vec2, Integer> colorMap = null;
        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 15; y++) {
                int col = this.customTexture.getPixelRGBA(x, y);
                colorMap.put(new Vec2(x, y), col);
            }
        }
        return colorMap;
    }

    public void drawMapPx(Map<Vec2, Integer> colorMap){
        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 15; y++) {
                int col = colorMap.get(new Vec2(x, y));
                drawPx(x, y, col);
            }
        }
    }





    public ResourceLocation TextureLocation = Minecraft.getInstance().getTextureManager().register("marker_ber_tex", this.dynamicTexture);

    //public void saveTexture(){
    //    this.TextureLocation.
    //}

    float scale = 1.0f;
    float yOffset = 0.0001f;

    @Override
    public void render(@NotNull MarkerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        VertexConsumer builder = bufferSource.getBuffer(RenderType.entityCutout(this.TextureLocation));
        System.out.println(this);
        drawQuad(builder, poseStack, 0f, 0 + yOffset, 0f, 1 * scale, 0 * scale + yOffset , 1 * scale, 0f, 0f, 1f, 1f, packedLight,1);
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
