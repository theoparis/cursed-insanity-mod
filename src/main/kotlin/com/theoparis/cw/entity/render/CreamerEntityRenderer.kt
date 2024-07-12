package com.theoparis.cw.entity.render

import com.theoparis.cw.entity.CreamerEntity
import com.theoparis.cw.entity.render.model.CreamerModel
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import org.joml.Vector3f
import software.bernie.geckolib.cache.`object`.BakedGeoModel
import software.bernie.geckolib.renderer.GeoEntityRenderer

@Environment(EnvType.CLIENT)
class CreamerEntityRenderer(
    ctx: EntityRendererFactory.Context,
) : GeoEntityRenderer<CreamerEntity>(
        ctx,
        CreamerModel(),
    ) {
    init {
        shadowRadius = 0.5f
    }

    fun getScale(
        animatable: CreamerEntity?,
        partialTicks: Float,
    ): Vector3f {
        if (animatable == null) return Vector3f()
        var g: Float = animatable.getClientFuseTime(partialTicks)
        val h = 1.0f + MathHelper.sin(g * 100.0f) * g * 0.01f
        g = MathHelper.clamp(g, 0.0f, 1.0f)
        g *= g
        g *= g
        val i = (1.0f + g * 0.4f) * h
        val j = (1.0f + g * 0.1f) / h
        return Vector3f(i, j, i)
    }

    override fun preRender(
        poseStack: MatrixStack,
        animatable: CreamerEntity,
        model: BakedGeoModel,
        bufferSource: VertexConsumerProvider?,
        buffer: VertexConsumer?,
        isReRender: Boolean,
        partialTick: Float,
        packedLight: Int,
        packedOverlay: Int,
        color: Int,
    ) {
        val sc = getScale(animatable, partialTick)
        poseStack.scale(sc.x, sc.y, sc.z)

        super.preRender(
            poseStack,
            animatable,
            model,
            bufferSource,
            buffer,
            isReRender,
            partialTick,
            packedLight,
            packedOverlay,
            color,
        )
    }

    init {
    }
}
