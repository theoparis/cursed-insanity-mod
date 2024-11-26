package com.theoparis.cw.entity.render.model

import com.theoparis.cw.CursedWeirdosMod
import com.theoparis.cw.entity.CreamerEntity
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel
import software.bernie.geckolib.renderer.GeoRenderer

class CreamerModel : GeoModel<CreamerEntity>() {
    companion object {
        @JvmStatic
        fun getTexture(): Identifier = Identifier.of(CursedWeirdosMod.modIdentifier, "textures/entity/creamer.png")
    }

    override fun getModelResource(
        obj: CreamerEntity,
        renderer: GeoRenderer<CreamerEntity>?,
    ): Identifier = Identifier.of(CursedWeirdosMod.modIdentifier, "geo/creamer.geo.json")

    override fun getTextureResource(
        obj: CreamerEntity,
        renderer: GeoRenderer<CreamerEntity>?,
    ): Identifier = getTexture()

    override fun getAnimationResource(obj: CreamerEntity): Identifier =
        Identifier.of(CursedWeirdosMod.modIdentifier, "animations/creamer.animation.json")
}
