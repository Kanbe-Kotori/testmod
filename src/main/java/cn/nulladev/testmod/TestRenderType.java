package cn.nulladev.testmod;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

import java.util.OptionalDouble;

public class TestRenderType extends RenderType {
    public TestRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static RenderType trinagleStrip() {
        return RenderType.create(
                "spell_blend_notex",
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.TRIANGLE_STRIP,
                1024, true, true,
                RenderType.CompositeState.builder()
                        .setTextureState(NO_TEXTURE)
                        .setShaderState(POSITION_COLOR_SHADER)
                        .setDepthTestState(NO_DEPTH_TEST)
                        .setCullState(NO_CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .createCompositeState(false)
        );
    }

    public static RenderType quads() {
        return RenderType.create(
                "spell_blend_notex",
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.QUADS,
                256, true, true,
                RenderType.CompositeState.builder()
                        .setTextureState(NO_TEXTURE)
                        .setShaderState(POSITION_COLOR_SHADER)
                        .setDepthTestState(NO_DEPTH_TEST)
                        .setCullState(NO_CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .createCompositeState(false)
        );
    }

    public static RenderType lineStrip(double width) {
        return RenderType.create(
                "spell_blend_notex",
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.LINE_STRIP,
                1024, true, true,
                RenderType.CompositeState.builder()
                        .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.of(width)))
                        .setTextureState(NO_TEXTURE)
                        .setShaderState(RENDERTYPE_LINES_SHADER)
                        .setDepthTestState(NO_DEPTH_TEST)
                        .setCullState(NO_CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                        .createCompositeState(false)
        );
    }
}
