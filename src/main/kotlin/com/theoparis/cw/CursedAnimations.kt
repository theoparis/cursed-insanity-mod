package com.theoparis.cw

import software.bernie.geckolib.core.animation.RawAnimation

object CursedAnimations {
    val creamerWalkAnimation = RawAnimation.begin().thenPlay("animation.creamer.walk")
    val imposterWalkAnimation = RawAnimation.begin().thenPlay("animation.imposter.walk")
}