package com.theoparis.cw.entity.render.model

import com.theoparis.cw.CursedWeirdosMod
import com.theoparis.cw.entity.CreamerEntity
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports
class CreamerModel : GeoModel<CreamerEntity>() {
    companion object {
        @JvmStatic
        fun getTexture() = Identifier.of(CursedWeirdosMod.modIdentifier, "textures/entity/creamer.png")
    }

    override fun getModelResource(obj: CreamerEntity): Identifier = Identifier.of(CursedWeirdosMod.modIdentifier, "geo/creamer.geo.json")

    override fun getTextureResource(obj: CreamerEntity): Identifier = getTexture()

    override fun getAnimationResource(obj: CreamerEntity): Identifier =
        Identifier.of(CursedWeirdosMod.modIdentifier, "animations/creamer.animation.json")
}
