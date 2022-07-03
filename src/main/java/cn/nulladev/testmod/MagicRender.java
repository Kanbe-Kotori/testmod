package cn.nulladev.testmod;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

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

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        stack.pushPose();
        stack.mulPose(Vector3f.YP.rotationDegrees(entity.getXRot() - 90.0F));
        stack.mulPose(Vector3f.ZP.rotationDegrees(entity.getYRot()));

        double d = 0.5D;
        double r1 = 2D;
        double w1 = 0.05D;

        int gradation = 30;
        VertexConsumer vc = buffer.getBuffer(TestRenderType.trinagleStrip());
        /*for (int i = 0; i < gradation; ++i) {
            stack.mulPose(Vector3f.XP.rotationDegrees(360.0F / gradation));
            vc.vertex(d, r1, 0).color(1F, 1F, 1F, 1F).endVertex();
            vc.vertex(d, r1 - w1, 0).color(1F, 1F, 1F, 1F).endVertex();
        }*/
        vc.vertex(0, 0, 0).color(1F, 1F, 1F, 1F).endVertex();
        vc.vertex(1, 0, 0).color(1F, 1F, 1F, 1F).endVertex();
        vc.vertex(0, 1, 0).color(1F, 1F, 1F, 1F).endVertex();

        stack.popPose();
    }
}
