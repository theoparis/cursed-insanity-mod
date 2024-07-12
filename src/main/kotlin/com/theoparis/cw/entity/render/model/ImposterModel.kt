package com.theoparis.cw.entity.render.model

import com.theoparis.cw.CursedWeirdosMod
import com.theoparis.cw.entity.ImposterEntity
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports
class ImposterModel : GeoModel<ImposterEntity>() {
    companion object {
        @JvmStatic
        fun getTexture() = Identifier.of(CursedWeirdosMod.modIdentifier, "textures/entity/imposter_gold.png")
    }

    override fun getModelResource(obj: ImposterEntity): Identifier = Identifier.of(CursedWeirdosMod.modIdentifier, "geo/imposter.geo.json")

    override fun getTextureResource(obj: ImposterEntity): Identifier = getTexture()

    override fun getAnimationResource(obj: ImposterEntity): Identifier =
        Identifier.of(CursedWeirdosMod.modIdentifier, "animations/imposter.animation.json")
}
