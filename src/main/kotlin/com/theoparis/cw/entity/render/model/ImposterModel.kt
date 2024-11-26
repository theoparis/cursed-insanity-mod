package com.theoparis.cw.entity.render.model

import com.theoparis.cw.CursedWeirdosMod
import com.theoparis.cw.entity.ImposterEntity
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel
import software.bernie.geckolib.renderer.GeoRenderer

class ImposterModel : GeoModel<ImposterEntity>() {
    companion object {
        @JvmStatic
        fun getTexture(): Identifier = Identifier.of(CursedWeirdosMod.modIdentifier, "textures/entity/imposter_gold.png")
    }

    override fun getModelResource(
        obj: ImposterEntity,
        p1: GeoRenderer<ImposterEntity>?,
    ): Identifier = Identifier.of(CursedWeirdosMod.modIdentifier, "geo/imposter.geo.json")

    override fun getTextureResource(
        obj: ImposterEntity,
        p1: GeoRenderer<ImposterEntity>?,
    ): Identifier = getTexture()

    override fun getAnimationResource(obj: ImposterEntity): Identifier =
        Identifier.of(CursedWeirdosMod.modIdentifier, "animations/imposter.animation.json")
}
