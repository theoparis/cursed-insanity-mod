package com.theoparis.cw.registry

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

object CursedSounds {
    private val CREAMER_AMBIENT_ID: Identifier = Identifier.of("cursedweirdos:creamer_ambient")
    val CREAMER_AMBIENT = SoundEvent.of(CREAMER_AMBIENT_ID)

    private val CREAMER_HURT_ID: Identifier = Identifier.of("cursedweirdos:creamer_hurt")
    val CREAMER_HURT = SoundEvent.of(CREAMER_HURT_ID)

    fun register() {
        Registry.register(Registries.SOUND_EVENT, CREAMER_AMBIENT_ID, CREAMER_AMBIENT)
        Registry.register(Registries.SOUND_EVENT, CREAMER_HURT_ID, CREAMER_HURT)
    }
}
