package cn.nulladev.testmod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MagicRender extends EntityRenderer<EntityMagic> {
    protected MagicRender(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMagic p_114482_) {
        return null;
    }

    @Override
    public void render(EntityMagic entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int p_114490_) {
        super.render(entity, entityYaw, partialTicks, stack, buffer, p_114490_);

        if (entity == null)
            return;

        stack.pushPose();
        stack.translate(0, entity.getOwner().getBbHeight() / 2, 0);
        stack.mulPose(Vector3f.YP.rotationDegrees(entity.getXRot() - 90.0F));
        stack.mulPose(Vector3f.ZP.rotationDegrees(entity.getYRot()));

        Matrix4f m = stack.last().pose();

        float d = 0.5F;
        float r1 = 2F;
        float w1 = 0.05F;

        int gradation = 120;
        float ang = (entity.tickCount % 200) / 200F * 360.0F;
        stack.mulPose(Vector3f.XP.rotationDegrees(ang));
        VertexConsumer vc = buffer.getBuffer(TestRenderType.trinagleStrip());
        for (int i = 0; i <= gradation; ++i) {
            stack.mulPose(Vector3f.XP.rotationDegrees(360.0F / gradation));
            int color = getColor((double)i / gradation);
            vc.vertex(m, d, r1, 0).color(color).endVertex();
            vc.vertex(m, d, r1 - w1, 0).color(color).endVertex();
        }

        stack.mulPose(Vector3f.XP.rotationDegrees(-2*ang));
        VertexConsumer vc1 = buffer.getBuffer(TestRenderType.lineStrip(8D));
        vc1.vertex(m, d, 2, 0).color(0xFFFFFFFF).endVertex();
        vc1.vertex(m, d, 0, 2).color(0xFFFFFFFF).endVertex();
        vc1.vertex(m, d, -2, 0).color(0xFFFFFFFF).endVertex();
        vc1.vertex(m, d, 0, -2).color(0xFFFFFFFF).endVertex();
        vc1.vertex(m, d, 2, 0).color(0xFFFFFFFF).endVertex();

        VertexConsumer vc2 = buffer.getBuffer(TestRenderType.lineStrip(8D));
        float sqrt2 = Mth.sqrt(2);
        vc2.vertex(m, d, sqrt2, sqrt2).color(0xFFFFFFFF).endVertex();
        vc2.vertex(m, d, sqrt2, -sqrt2).color(0xFFFFFFFF).endVertex();
        vc2.vertex(m, d, -sqrt2, -sqrt2).color(0xFFFFFFFF).endVertex();
        vc2.vertex(m, d, -sqrt2, sqrt2).color(0xFFFFFFFF).endVertex();
        vc2.vertex(m, d, sqrt2, sqrt2).color(0xFFFFFFFF).endVertex();

        stack.popPose();
    }

    public static int getColor(double angle) {
        int a = 0xFF;
        int r = 0, g = 0, b = 0;
        double p = (angle * 6) % 1;
        double mp = 1 - p;
        if (angle < 1D/6) {
            r = 0xFF;
            g = (int) (0xFF * p);
        } else if (angle < 2D/6) {
            r = (int) (0xFF * mp);
            g = 0xFF;
        } else if (angle < 3D/6) {
            g = 0xFF;
            b = (int) (0xFF * p);
        } else if (angle < 4D/6) {
            g = (int) (0xFF * mp);
            b = 0xFF;
        } else if (angle < 5D/6) {
            r = (int) (0xFF * p);
            b = 0xFF;
        } else if (angle < 1D) {
            r = 0xFF;
            b = (int) (0xFF * mp);
        } else {
            r = 0xFF;
        }
        return a << 24 | r << 16 | g << 8 | b;
    }
}
