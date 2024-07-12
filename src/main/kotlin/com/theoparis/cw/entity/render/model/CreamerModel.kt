package com.theoparis.cw.entity.render.model

import com.theoparis.cw.CursedWeirdosMod
import com.theoparis.cw.entity.CreamerEntity
import net.minecraft.util.Identifier
import software.bernie.geckolib3.model.AnimatedGeoModel

// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports
class CreamerModel : AnimatedGeoModel<CreamerEntity>() {
    companion object {
        @JvmStatic
        fun getTexture() = Identifier(CursedWeirdosMod.modIdentifier, "textures/entity/creamer.png")
    }

    override fun getModelLocation(obj: CreamerEntity): Identifier = Identifier(CursedWeirdosMod.modIdentifier, "geo/creamer.geo.json")

    override fun getTextureLocation(obj: CreamerEntity): Identifier = getTexture()

    override fun getAnimationFileLocation(obj: CreamerEntity): Identifier =
        Identifier(CursedWeirdosMod.modIdentifier, "animations/creamer.animation.json")
}
