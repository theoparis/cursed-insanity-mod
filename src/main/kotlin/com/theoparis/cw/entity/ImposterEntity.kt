package com.theoparis.cw.entity

import com.theoparis.cw.CursedAnimations
import com.theoparis.cw.CursedWeirdosMod
import net.minecraft.entity.EntityType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager
import software.bernie.geckolib.core.animation.AnimationController
import software.bernie.geckolib.core.animation.AnimationState
import software.bernie.geckolib.core.`object`.PlayState
import software.bernie.geckolib.util.GeckoLibUtil

class ImposterEntity(entityType: EntityType<out HostileEntity>?, world: World?) :
    HostileEntity(entityType, world),
    GeoEntity {
    private val cache = GeckoLibUtil.createInstanceCache(this)

    override fun initGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(4, MeleeAttackGoal(this, 1.0, false))
        goalSelector.add(5, WanderAroundFarGoal(this, 0.8))
        goalSelector.add(6, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(6, LookAroundGoal(this))
        targetSelector.add(
            1,
            TargetGoal(
                this,
                PlayerEntity::class.java, true
            )
        )
        targetSelector.add(2, RevengeGoal(this, *arrayOfNulls(0)))
    }

    private fun shouldPlayWalkAnim() = !navigation.isIdle || velocity.x > 0 || velocity.z > 0 || velocity.y > 0

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar?) {
        controllers?.add(
            AnimationController(
                this, "animation.imposter.walk", 5
            ) { state: AnimationState<ImposterEntity> ->
                if (shouldPlayWalkAnim())
                    state.setAndContinue(
                        CursedAnimations.imposterWalkAnimation
                    )
                else PlayState.CONTINUE
            }
        )
    }

    init {
        this.equipStack(EquipmentSlot.MAINHAND, ItemStack(Items.IRON_SWORD))
    }

    override fun dropEquipment(source: DamageSource?, lootingMultiplier: Int, allowDrops: Boolean) {
        super.dropEquipment(source, lootingMultiplier, allowDrops)
        dropItem {
            CursedWeirdosMod.totemOfLying
        }
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }

    companion object {
        fun createAttributes(): DefaultAttributeContainer.Builder {
            return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
        }
    }
}
