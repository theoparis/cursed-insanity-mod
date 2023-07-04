package com.theoparis.cw.entity.render.base

import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.LivingEntity
import software.bernie.geckolib.core.animatable.GeoAnimatable
import software.bernie.geckolib.model.GeoModel
import software.bernie.geckolib.renderer.GeoEntityRenderer

open class AnimatedEntityRenderer<T>(ctx: EntityRendererFactory.Context, modelProvider: GeoModel<T>) :
    GeoEntityRenderer<T>(ctx, modelProvider) where
T : GeoAnimatable, T : LivingEntity {
    private val features = mutableListOf<AnimatedFeatureRenderer<T>>()

    fun getFeatures() = features.toList()

    fun addFeature(newFeature: AnimatedFeatureRenderer<T>): Boolean = features.add(newFeature)

    override fun render(
        entity: T,
        entityYaw: Float,
        partialTick: Float,
        matrixStack: MatrixStack?,
        bufferSource: VertexConsumerProvider?,
        packedLight: Int
    ) {
        super.render(entity, entityYaw, partialTick, matrixStack, bufferSource, packedLight)

        features.forEach {
            it.render(animatable, matrixStack, partialTick, bufferSource, packedLight)
        }
    }
}

interface AnimatedFeatureRenderer<T> where
T : LivingEntity,
T : GeoAnimatable {
    fun render(
        entity: T,
        matrices: MatrixStack?,
        ticks: Float,
        provider: VertexConsumerProvider?,
        light: Int
    )
}
