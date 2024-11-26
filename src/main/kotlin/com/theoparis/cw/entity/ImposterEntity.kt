package com.theoparis.cw.entity

import com.theoparis.cw.CursedWeirdosMod
import net.minecraft.entity.EntityType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.ai.goal.ActiveTargetGoal
import net.minecraft.entity.ai.goal.LookAroundGoal
import net.minecraft.entity.ai.goal.LookAtEntityGoal
import net.minecraft.entity.ai.goal.MeleeAttackGoal
import net.minecraft.entity.ai.goal.RevengeGoal
import net.minecraft.entity.ai.goal.SwimGoal
import net.minecraft.entity.ai.goal.WanderAroundFarGoal
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.constant.DefaultAnimations
import software.bernie.geckolib.util.GeckoLibUtil

class ImposterEntity(
    entityType: EntityType<out HostileEntity>?,
    world: World?,
) : HostileEntity(entityType, world),
    GeoEntity {
    private val animatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun initGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(4, MeleeAttackGoal(this, 1.0, false))
        goalSelector.add(5, WanderAroundFarGoal(this, 0.8))
        goalSelector.add(6, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(6, LookAroundGoal(this))
        targetSelector.add(
            1,
            ActiveTargetGoal(
                this,
                PlayerEntity::class.java,
                true,
            ),
        )
        targetSelector.add(2, RevengeGoal(this, *arrayOfNulls(0)))
    }

    private fun shouldPlayWalkAnim() = !navigation.isIdle || velocity.x > 0 || velocity.z > 0 || velocity.y > 0

    init {
        this.equipStack(EquipmentSlot.MAINHAND, ItemStack(Items.IRON_SWORD))
    }

    override fun dropEquipment(
        world: ServerWorld,
        source: DamageSource,
        causedByPlayer: Boolean,
    ) {
        super.dropEquipment(world, source, causedByPlayer)

        dropItem(
            world,
            CursedWeirdosMod.totemOfLying,
        )
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = animatableInstanceCache

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(
            DefaultAnimations.genericWalkIdleController(this),
        )
    }

    companion object {
        fun createAttributes(): DefaultAttributeContainer.Builder =
            createHostileAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.35)
                .add(EntityAttributes.MAX_HEALTH, 8.0)
    }
}
