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
        stack.translate(0, entity.getOwner().getEyeHeight(), 0);
        stack.mulPose(Vector3f.YP.rotationDegrees(entity.getXRot() - 90.0F));
        stack.mulPose(Vector3f.ZP.rotationDegrees(entity.getYRot()));

        Matrix4f m = stack.last().pose();

        float d = 2F;
        float r1 = 2.2F;
        float r2 = 1.8F;
        float w1 = 0.04F;
        float w2 = 0.02F;

        round2d(buffer, m, d, 0, 0, r1, w1, 120, 0x3FFFFFFF);
        round2d(buffer, m, d, 0, 0, r2, w1, 120, 0x3FFFFFFF);

        float tickRot = (entity.tickCount % 200) / 200F * 360.0F;
        stack.mulPose(Vector3f.XP.rotationDegrees(tickRot));

        float r12 = (r1 + r2) / 2;
        float rs = (r1 - r2) / 2;
        float[] sin2pi_7 = new float[7];
        float[] cos2pi_7 = new float[7];
        for (int i = 0; i < 7; i++) {
            sin2pi_7[i] = Mth.sin(i*2*Mth.PI/7);
            cos2pi_7[i] = Mth.cos(i*2*Mth.PI/7);
        }

        round2d(buffer, m, d, r12 * sin2pi_7[0], r12 * cos2pi_7[0], rs, w2, 60, 0x7FFF0000);
        round2d(buffer, m, d, r12 * sin2pi_7[1], r12 * cos2pi_7[1], rs, w2, 60, 0x7FFF7F00);
        round2d(buffer, m, d, r12 * sin2pi_7[2], r12 * cos2pi_7[2], rs, w2, 60, 0x7FFFFF00);
        round2d(buffer, m, d, r12 * sin2pi_7[3], r12 * cos2pi_7[3], rs, w2, 60, 0x7F00FF00);
        round2d(buffer, m, d, r12 * sin2pi_7[4], r12 * cos2pi_7[4], rs, w2, 60, 0x7F00FFFF);
        round2d(buffer, m, d, r12 * sin2pi_7[5], r12 * cos2pi_7[5], rs, w2, 60, 0x7F0000FF);
        round2d(buffer, m, d, r12 * sin2pi_7[6], r12 * cos2pi_7[6], rs, w2, 60, 0x7F7F00FF);

        line2d(buffer,m,d,r12 * sin2pi_7[0], r12 * cos2pi_7[0],r12 * sin2pi_7[3], r12 * cos2pi_7[3],w1,0x7FFF0000,0x7F00FF00);
        line2d(buffer,m,d,r12 * sin2pi_7[3], r12 * cos2pi_7[3],r12 * sin2pi_7[6], r12 * cos2pi_7[6],w1,0x7F00FF00,0x7F7F00FF);
        line2d(buffer,m,d,r12 * sin2pi_7[6], r12 * cos2pi_7[6],r12 * sin2pi_7[2], r12 * cos2pi_7[2],w1,0x7F7F00FF,0x7FFFFF00);
        line2d(buffer,m,d,r12 * sin2pi_7[2], r12 * cos2pi_7[2],r12 * sin2pi_7[5], r12 * cos2pi_7[5],w1,0x7FFFFF00,0x7F0000FF);
        line2d(buffer,m,d,r12 * sin2pi_7[5], r12 * cos2pi_7[5],r12 * sin2pi_7[1], r12 * cos2pi_7[1],w1,0x7F0000FF,0x7FFF7F00);
        line2d(buffer,m,d,r12 * sin2pi_7[1], r12 * cos2pi_7[1],r12 * sin2pi_7[4], r12 * cos2pi_7[4],w1,0x7FFF7F00,0x7F00FFFF);
        line2d(buffer,m,d,r12 * sin2pi_7[4], r12 * cos2pi_7[4],r12 * sin2pi_7[0], r12 * cos2pi_7[0],w1,0x7F00FFFF,0x7FFF0000);

        stack.mulPose(Vector3f.XP.rotationDegrees(-5*tickRot));

        float ri = r12 * Mth.cos(3 * Mth.PI / 7);

        round2dRainbow(buffer, m, d, 0, 0, ri, w1, 120);

        stack.popPose();
    }

    public static void round2d(MultiBufferSource buffer, Matrix4f m, float x, float y, float z, float r, float w, int gradation, int color) {
        VertexConsumer vc = buffer.getBuffer(TestRenderType.trinagleStrip());
        for (int i = 0; i <= gradation; ++i) {
            float yPos = y + r * Mth.sin(i * 2 * Mth.PI / gradation);
            float zPos = z + r * Mth.cos(i * 2 * Mth.PI / gradation);
            float yOffset = w / 2 * Mth.sin(i * 2 * Mth.PI / gradation);
            float zOffset = w / 2 * Mth.cos(i * 2 * Mth.PI / gradation);
            vc.vertex(m, x, yPos - yOffset, zPos - zOffset).color(color).endVertex();
            vc.vertex(m, x, yPos + yOffset, zPos + zOffset).color(color).endVertex();
        }
    }

    public static void round2dRainbow(MultiBufferSource buffer, Matrix4f m, float x, float y, float z, float r, float w, int gradation) {
        VertexConsumer vc = buffer.getBuffer(TestRenderType.trinagleStrip());
        for (int i = 0; i <= gradation; ++i) {
            int color = getRainbowColor((double)i / gradation);
            float yPos = y + r * Mth.sin(i * 2 * Mth.PI / gradation);
            float zPos = z + r * Mth.cos(i * 2 * Mth.PI / gradation);
            float yOffset = w / 2 * Mth.sin(i * 2 * Mth.PI / gradation);
            float zOffset = w / 2 * Mth.cos(i * 2 * Mth.PI / gradation);
            vc.vertex(m, x, yPos - yOffset, zPos - zOffset).color(color).endVertex();
            vc.vertex(m, x, yPos + yOffset, zPos + zOffset).color(color).endVertex();
        }
    }

    public static void line2d(MultiBufferSource buffer, Matrix4f m, float x, float y1, float z1, float y2, float z2, float width, int color1, int color2) {
        VertexConsumer vc = buffer.getBuffer(TestRenderType.quads());
        float dy = y1 - y2;
        float dz = z1 - z2;
        float yz = Mth.sqrt(dy * dy + dz * dz);

        float wdy = width * dz / yz / 2;
        float wdz = -width * dy / yz / 2;

        vc.vertex(m, x, y1 - wdy, z1 - wdz).color(color1).endVertex();
        vc.vertex(m, x, y2 - wdy, z2 - wdz).color(color2).endVertex();
        vc.vertex(m, x, y2 + wdy, z2 + wdz).color(color2).endVertex();
        vc.vertex(m, x, y1 + wdy, z1 + wdz).color(color1).endVertex();
    }

    public static int getRainbowColor(double angle) {
        int a = 0x7F;
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
