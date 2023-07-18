package com.theoparis.cw.registry

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

object CursedSounds {
    private val creamerAmbientSoundId: Identifier = Identifier("cursedweirdos:creamer_ambient")
    val creamerAmbientSound: SoundEvent = SoundEvent.createVariableRangeEvent(creamerAmbientSoundId)

    private val creamerHurtSoundId: Identifier = Identifier("cursedweirdos:creamer_hurt")
    val creamerHurtSound: SoundEvent = SoundEvent.createVariableRangeEvent(creamerHurtSoundId)

    fun register() {
        Registry.register(Registries.SOUND_EVENT, creamerAmbientSoundId, creamerAmbientSound)
        Registry.register(Registries.SOUND_EVENT, creamerHurtSoundId, creamerHurtSound)
    }
}
