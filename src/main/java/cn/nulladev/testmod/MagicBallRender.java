package cn.nulladev.testmod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MagicBallRender extends EntityRenderer<EntityMagicBall> {

    public static final float SIZE = 0.2F;

    protected MagicBallRender(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMagicBall p_114482_) {
        return null;
    }

    @Override
    public void render(EntityMagicBall entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int p_114490_) {
        super.render(entity, entityYaw, partialTicks, stack, buffer, p_114490_);

        stack.pushPose();
        Matrix4f m = stack.last().pose();

        sphere(buffer, m, 0, 0, 0, SIZE, 30, entity.getColor());

        stack.popPose();
    }

    public static void sphere(MultiBufferSource buffer, Matrix4f m, float x, float y, float z, float r, int gradation, int color) {
        VertexConsumer vc = buffer.getBuffer(TestRenderType.trinagleStrip());
        for (int i = 0; i < gradation; i++) {
            float alpha = (float) (i * Math.PI / gradation);
            for (int j = 0; j <= gradation; j++) {
                float beta = j * 2 * Mth.PI / gradation;
                float x1 = x + r * Mth.cos(beta) * Mth.sin(alpha);
                float y1 = y + r * Mth.sin(beta) * Mth.sin(alpha);
                float z1 = z + r * Mth.cos(alpha);
                vc.vertex(m, x1, y1, z1).color(color).endVertex();
                x1 = x + r * Mth.cos(beta) * Mth.sin(alpha + Mth.PI / gradation);
                y1 = y + r * Mth.sin(beta) * Mth.sin(alpha + Mth.PI / gradation);
                z1 = z + r * Mth.cos(alpha + Mth.PI / gradation);
                vc.vertex(m, x1, y1, z1).color(color).endVertex();
            }
        }
    }

}
