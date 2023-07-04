package com.theoparis.cw.entity.render

import com.theoparis.cw.entity.ImposterEntity
import com.theoparis.cw.entity.render.base.AnimatedEntityRenderer
import com.theoparis.cw.entity.render.base.feature.HeldItemFeature
import com.theoparis.cw.entity.render.model.ImposterModel
import net.minecraft.client.render.entity.EntityRendererFactory
import org.joml.Vector3f

class ImposterEntityRenderer(ctx: EntityRendererFactory.Context) :
    AnimatedEntityRenderer<ImposterEntity>(
        ctx,
        ImposterModel()
    ) {

    init {
        addFeature(HeldItemFeature(Vector3f(0.5f, 0.5f, 0f)))
    }

    companion object
}
