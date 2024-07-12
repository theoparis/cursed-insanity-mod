package com.theoparis.cw.entity.render.base

import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.LivingEntity
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.model.GeoModel
import software.bernie.geckolib.renderer.GeoEntityRenderer

open class AnimatedEntityRenderer<T>(
    ctx: EntityRendererFactory.Context,
    modelProvider: GeoModel<T>,
) : GeoEntityRenderer<T>(ctx, modelProvider) where
          T : LivingEntity,
          T : GeoEntity {
    private val features = mutableListOf<AnimatedFeatureRenderer<T>>()

    fun getFeatures() = features.toList()

    fun addFeature(newFeature: AnimatedFeatureRenderer<T>): Boolean = features.add(newFeature)

    override fun render(
        animatable: T,
        entityYaw: Float,
        partialTick: Float,
        poseStack: MatrixStack,
        bufferSource: VertexConsumerProvider,
        packedLight: Int,
    ) {
        super.render(
            animatable,
            entityYaw,
            partialTick,
            poseStack,
            bufferSource,
            packedLight,
        )

        features.forEach {
            it.render(animatable, poseStack, partialTick, bufferSource, packedLight)
        }
    }
}

interface AnimatedFeatureRenderer<T> where
          T : LivingEntity,
          T : GeoEntity {
    fun render(
        entity: T,
        matrices: MatrixStack?,
        ticks: Float,
        provider: VertexConsumerProvider?,
        light: Int,
    )
}
