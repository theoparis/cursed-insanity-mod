package com.theoparis.cw.entity.render.base.feature

import com.theoparis.cw.entity.render.base.AnimatedFeatureRenderer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.LivingEntity
import org.joml.Vector3f
import software.bernie.geckolib.core.animatable.GeoAnimatable

class HeldItemFeature<T>(private val translation: Vector3f) :
    AnimatedFeatureRenderer<T> where T : GeoAnimatable, T : LivingEntity {
    private val mc = MinecraftClient.getInstance()

    override fun render(
        entity: T,
        matrices: MatrixStack?,
        ticks: Float,
        provider: VertexConsumerProvider?,
        light: Int
    ) {
        matrices?.push()
        matrices?.scale(0.75f, 0.75f, 0.75f)
        matrices?.translate(translation.x.toDouble(), translation.y.toDouble(), translation.z.toDouble())
        mc.itemRenderer.renderItem(
            entity,
            entity.mainHandStack,
            ModelTransformationMode.THIRD_PERSON_RIGHT_HAND,
            false,
            matrices,
            provider,
            entity.world,
            light,
            0,
            0
        )
        matrices?.pop()
    }
}
