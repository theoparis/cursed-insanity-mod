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
        fun getTexture() = Identifier(CursedWeirdosMod.modID, "textures/entity/creamer.png")
    }

    override fun getModelResource(animatable: CreamerEntity?): Identifier =
        Identifier(CursedWeirdosMod.modID, "geo/creamer.geo.json")

    override fun getTextureResource(animatable: CreamerEntity?): Identifier =
        getTexture()

    override fun getAnimationResource(animatable: CreamerEntity?): Identifier =
        Identifier(CursedWeirdosMod.modID, "animations/creamer.animation.json")
}
