package com.theoparis.cw.entity.render.model

import com.theoparis.cw.CursedWeirdosMod
import com.theoparis.cw.entity.ImposterEntity
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

class ImposterModel : GeoModel<ImposterEntity>() {

    companion object {
        @JvmStatic
        fun getTexture() = Identifier(CursedWeirdosMod.modID, "textures/entity/imposter_gold.png")
    }

    override fun getModelResource(animatable: ImposterEntity?): Identifier =
        Identifier(CursedWeirdosMod.modID, "geo/imposter.geo.json")


    override fun getTextureResource(animatable: ImposterEntity?): Identifier =
        getTexture()


    override fun getAnimationResource(animatable: ImposterEntity?): Identifier =
        Identifier(CursedWeirdosMod.modID, "animations/imposter.animation.json")
}
