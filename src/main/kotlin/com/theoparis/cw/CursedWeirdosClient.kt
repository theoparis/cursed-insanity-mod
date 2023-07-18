package com.theoparis.cw

import com.theoparis.cw.entity.render.CreamerEntityRenderer
import com.theoparis.cw.entity.render.ImposterEntityRenderer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.EntityRendererFactory
import org.apache.logging.log4j.LogManager
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer
import software.bernie.geckolib.GeckoLib

class CursedWeirdosClient : ClientModInitializer {
    companion object {
        @JvmStatic
        val logger = LogManager.getFormatterLogger(CursedWeirdosMod.modID)
    }

    override fun onInitializeClient(mod: ModContainer?) {
        GeckoLib.initialize()

        EntityRendererRegistry.register(
            CursedWeirdosMod.creamerEntity
        ) { ctx: EntityRendererFactory.Context ->
            CreamerEntityRenderer(
                ctx
            )
        }

        EntityRendererRegistry.register(
            CursedWeirdosMod.imposterEntity
        ) { ctx: EntityRendererFactory.Context ->
            ImposterEntityRenderer(
                ctx
            )
        }
    }
}
