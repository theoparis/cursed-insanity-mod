package com.theoparis.cw

import com.theoparis.cw.entity.CreamerEntity
import com.theoparis.cw.entity.ImposterEntity
import com.theoparis.cw.item.CreamJarItem
import com.theoparis.cw.registry.CursedSounds
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.attribute.DefaultAttributeRegistry
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.world.biome.Biomes
import org.apache.logging.log4j.LogManager
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors

class CursedWeirdosMod : ModInitializer {
    companion object {
        @JvmStatic
        val modID = "cursedweirdos"

        val creamJarItem = CreamJarItem(Item.Settings())
        val cucumberItem = Item(Item.Settings())
        val totemOfLying = Item(Item.Settings())

        val creamerEntity: EntityType<CreamerEntity> = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier(modID, "creamer"),
            QuiltEntityTypeBuilder.create(SpawnGroup.MONSTER, ::CreamerEntity)
                .apply {
                    this.setDimensions(
                        EntityDimensions.fixed(0.6f, 1.7f)
                    )
                    this.maxBlockTrackingRange(8)
                }
                .build()
        )

        val imposterEntity: EntityType<ImposterEntity> = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier(modID, "imposter"),
            QuiltEntityTypeBuilder.create(SpawnGroup.MONSTER, ::ImposterEntity)
                .apply {
                    this.setDimensions(
                        EntityDimensions.fixed(1f, 1f)
                    )
                    this.maxBlockTrackingRange(8)
                }
                .build()
        )

        @JvmStatic
        val logger = LogManager.getFormatterLogger(modID)

        // Define your registry items here (eg. blocks)
    }

    override fun onInitialize(mod: ModContainer?) {
        Registry.register(Registries.ITEM, Identifier(modID, "cream_jar"), creamJarItem)
        Registry.register(Registries.ITEM, Identifier(modID, "cucumber"), cucumberItem)
        Registry.register(Registries.ITEM, Identifier(modID, "totem_of_lying"), totemOfLying)
        DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY[creamerEntity] = CreamerEntity.createAttributes().build()
        DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY[imposterEntity] = ImposterEntity.createAttributes().build()
        CursedSounds.register()
        BiomeModifications.addSpawn(
            BiomeSelectors.foundInOverworld()
                .and(
                    BiomeSelectors.includeByKey(Biomes.PLAINS)
                        .or(
                            BiomeSelectors.includeByKey(Biomes.FOREST)
                        )
                        .or(
                            BiomeSelectors.includeByKey(Biomes.TAIGA)
                        )
                        .or(
                            BiomeSelectors.includeByKey(Biomes.JAGGED_PEAKS)
                        )
                ),
            SpawnGroup.MONSTER,
            creamerEntity,
            15,
            1,
            3
        )
        BiomeModifications.addSpawn(
            BiomeSelectors.foundInOverworld()
                .and(
                    BiomeSelectors.includeByKey(Biomes.PLAINS)
                        .or(
                            BiomeSelectors.includeByKey(Biomes.FOREST)
                        )
                        .or(
                            BiomeSelectors.includeByKey(Biomes.TAIGA)
                        )
                        .or(
                            BiomeSelectors.includeByKey(Biomes.JAGGED_PEAKS)
                        )
                ),
            SpawnGroup.MONSTER,
            imposterEntity,
            11,
            1,
            2
        )
    }
}
