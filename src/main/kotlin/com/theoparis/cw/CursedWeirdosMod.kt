package com.theoparis.cw

import com.theoparis.cw.entity.CreamerEntity
import com.theoparis.cw.entity.ImposterEntity
import com.theoparis.cw.item.CreamJarItem
import com.theoparis.cw.registry.CursedSounds
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.world.biome.BiomeKeys
import org.apache.logging.log4j.LogManager

class CursedWeirdosMod : ModInitializer {
    companion object {
        @JvmStatic
        val modIdentifier = "cursedweirdos"

        val creamJarItem =
            CreamJarItem(
                Item
                    .Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modIdentifier, "cream_jar")))
                    .maxCount(1),
            )
        val cucummberItem =
            Item(
                Item
                    .Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modIdentifier, "cucummber"))),
            )
        val totemOfLying =
            Item(
                Item
                    .Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modIdentifier, "totem_of_lying")))
                    .maxCount(1),
            )

        val creamerEntity: EntityType<CreamerEntity> =
            Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(modIdentifier, "creamer"),
                EntityType.Builder
                    .create(::CreamerEntity, SpawnGroup.MONSTER)
                    .dimensions(
                        0.6f,
                        1.7f,
                    ).maxTrackingRange(8)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(modIdentifier, "creamer"))),
            )

        val imposterEntity: EntityType<ImposterEntity> =
            Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(modIdentifier, "imposter"),
                EntityType.Builder
                    .create(::ImposterEntity, SpawnGroup.MONSTER)
                    .dimensions(
                        1f,
                        1f,
                    ).maxTrackingRange(8)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(modIdentifier, "imposter"))),
            )

        @JvmStatic
        val logger = LogManager.getFormatterLogger(modIdentifier)
    }

    override fun onInitialize() {
        Registry.register(Registries.ITEM, Identifier.of(modIdentifier, "cream_jar"), creamJarItem)
        Registry.register(Registries.ITEM, Identifier.of(modIdentifier, "cucummber"), cucummberItem)
        Registry.register(Registries.ITEM, Identifier.of(modIdentifier, "totem_of_lying"), totemOfLying)
        FabricDefaultAttributeRegistry.register(creamerEntity, CreamerEntity.createAttributes())
        FabricDefaultAttributeRegistry.register(imposterEntity, ImposterEntity.createAttributes())
        CursedSounds.register()
        BiomeModifications.addSpawn(
            BiomeSelectors
                .foundInOverworld()
                .and(
                    BiomeSelectors.includeByKey(
                        BiomeKeys.PLAINS,
                        BiomeKeys.FOREST,
                        BiomeKeys.TAIGA,
                        BiomeKeys.WINDSWEPT_HILLS,
                    ),
                ),
            SpawnGroup.MONSTER,
            creamerEntity,
            15,
            1,
            3,
        )
        BiomeModifications.addSpawn(
            BiomeSelectors
                .foundInOverworld()
                .and(
                    BiomeSelectors.includeByKey(
                        BiomeKeys.PLAINS,
                        BiomeKeys.FOREST,
                        BiomeKeys.TAIGA,
                        BiomeKeys.WINDSWEPT_HILLS,
                    ),
                ),
            SpawnGroup.MONSTER,
            imposterEntity,
            11,
            1,
            2,
        )
    }
}
