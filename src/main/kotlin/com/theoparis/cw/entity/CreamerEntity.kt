package com.theoparis.cw.entity

import com.theoparis.cw.CursedWeirdosMod
import com.theoparis.cw.entity.goal.ExplosiveIgniteGoal
import com.theoparis.cw.entity.util.IExplosiveEntity
import com.theoparis.cw.registry.CursedSounds
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.AreaEffectCloudEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LightningEntity
import net.minecraft.entity.ai.goal.ActiveTargetGoal
import net.minecraft.entity.ai.goal.FleeEntityGoal
import net.minecraft.entity.ai.goal.LookAroundGoal
import net.minecraft.entity.ai.goal.LookAtEntityGoal
import net.minecraft.entity.ai.goal.MeleeAttackGoal
import net.minecraft.entity.ai.goal.RevengeGoal
import net.minecraft.entity.ai.goal.SwimGoal
import net.minecraft.entity.ai.goal.WanderAroundFarGoal
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.passive.CatEntity
import net.minecraft.entity.passive.OcelotEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.MathHelper
import net.minecraft.world.GameRules
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.constant.DefaultAnimations
import software.bernie.geckolib.util.GeckoLibUtil

class CreamerEntity(
    entityType: EntityType<out HostileEntity>,
    world: World,
) : HostileEntity(entityType, world),
    IExplosiveEntity,
    GeoEntity {
    private var lastFuseTime = 0
    private var currentFuseTime = 0
    override var fuseTime = 30

    private var explosionRadius = 3
    private var headsDropped = 0
    private val animatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun initGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(2, ExplosiveIgniteGoal(this))
        goalSelector.add(3, FleeEntityGoal(this, OcelotEntity::class.java, 6.0f, 1.0, 1.2))
        goalSelector.add(3, FleeEntityGoal(this, CatEntity::class.java, 6.0f, 1.0, 1.2))
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

    override fun getSafeFallDistance(): Int = if (target == null) 3 else 3 + (this.health - 1.0f).toInt()

    override fun handleFallDamage(
        fallDistance: Float,
        damageMultiplier: Float,
        source: DamageSource,
    ): Boolean {
        val bl = super.handleFallDamage(fallDistance, damageMultiplier, source)
        currentFuseTime = (currentFuseTime.toFloat() + fallDistance * 1.5f).toInt()
        if (currentFuseTime > fuseTime - 5) {
            currentFuseTime = fuseTime - 5
        }
        return bl
    }

    override fun initDataTracker(builder: DataTracker.Builder) {
        super.initDataTracker(builder)
        builder.add(fuseSpeedTracker, -1)
        builder.add(chargedTracker, false)
        builder.add(ignitedTracker, false)
    }

    override fun writeCustomDataToNbt(tag: NbtCompound) {
        super.writeCustomDataToNbt(tag)
        if (dataTracker.get(chargedTracker) as Boolean) {
            tag.putBoolean("powered", true)
        }

        tag.putShort("fuse", fuseTime.toShort())
        tag.putByte("explosionRadius", explosionRadius.toByte())
        tag.putBoolean("ignited", ignited)
    }

    override fun readCustomDataFromNbt(tag: NbtCompound) {
        super.readCustomDataFromNbt(tag)
        dataTracker.set(chargedTracker, tag.getBoolean("powered"))
        if (tag.contains("fuse", 99)) {
            fuseTime = tag.getShort("fuse").toInt()
        }

        if (tag.contains("explosionRadius", 99)) {
            explosionRadius = tag.getByte("explosionRadius").toInt()
        }

        if (tag.getBoolean("ignited")) {
            ignite()
        }
    }

    override fun tick() {
        if (this.isAlive) {
            lastFuseTime = currentFuseTime
            if (ignited) {
                fuseSpeed = 1
            }
            val i = fuseSpeed
            if (i > 0 && currentFuseTime == 0) {
                playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0f, 0.5f)
            }
            currentFuseTime += i
            if (currentFuseTime < 0) {
                currentFuseTime = 0
            }
            if (currentFuseTime > fuseTime / 2) {
                world.addParticle(ParticleTypes.CLOUD, true, pos.x, pos.y, pos.z, 0.1, 0.2, 0.1)
            }
            if (currentFuseTime >= fuseTime) {
                currentFuseTime = fuseTime
                explode()
            }
        }
        super.tick()
    }

    override fun getHurtSound(source: DamageSource): SoundEvent = CursedSounds.CREAMER_HURT

    override fun getAmbientSound(): SoundEvent = CursedSounds.CREAMER_AMBIENT

    override fun getDeathSound(): SoundEvent = CursedSounds.CREAMER_HURT

    override fun dropEquipment(
        world: ServerWorld,
        source: DamageSource,
        causedByPlayer: Boolean,
    ) {
        super.dropEquipment(world, source, causedByPlayer)

        val entity = source.attacker
        if (entity !== this) {
            this.dropItem(world, CursedWeirdosMod.cucummberItem)
        }
    }

    @Environment(EnvType.CLIENT)
    fun shouldRenderOverlay(): Boolean = dataTracker.get(chargedTracker) as Boolean

    @Environment(EnvType.CLIENT)
    fun getClientFuseTime(timeDelta: Float): Float =
        MathHelper.lerp(timeDelta, lastFuseTime.toFloat(), currentFuseTime.toFloat()) / (fuseTime - 2).toFloat()

    override var fuseSpeed: Int
        get() = dataTracker.get(fuseSpeedTracker) as Int
        set(fuseSpeed) {
            dataTracker.set(fuseSpeedTracker, fuseSpeed)
        }

    override fun onStruckByLightning(
        world: ServerWorld,
        lightning: LightningEntity,
    ) {
        super.onStruckByLightning(world, lightning)
        dataTracker.set(chargedTracker, true)
    }

    override fun interactMob(
        player: PlayerEntity,
        hand: Hand,
    ): ActionResult {
        val itemStack = player.getStackInHand(hand)
        return if (itemStack.item === Items.FLINT_AND_STEEL) {
            world.playSound(
                player,
                this.x,
                this.y,
                this.z,
                SoundEvents.ITEM_FLINTANDSTEEL_USE,
                this.soundCategory,
                1.0f,
                random.nextFloat() * 0.4f + 0.8f,
            )
            if (!world.isClient) {
                ignite()
                itemStack.damage(
                    1,
                    world as ServerWorld,
                    player as ServerPlayerEntity,
                ) { _: Item ->
                }
            }
            ActionResult.SUCCESS
        } else {
            super.interactMob(player, hand)
        }
    }

    private fun explode() {
        if (!world.isClient) {
            val destructionType =
                if ((world as ServerWorld).gameRules.getBoolean(GameRules.DO_MOB_GRIEFING)) {
                    World.ExplosionSourceType.MOB
                } else {
                    World.ExplosionSourceType.NONE
                }
            val f = if (shouldRenderOverlay()) 2.0f else 1.0f
            dead = true
            world.createExplosion(this, this.x, this.y, this.z, explosionRadius.toFloat() * f, destructionType)
            this.remove(RemovalReason.KILLED)
            spawnEffectsCloud()
        }
    }

    private fun spawnEffectsCloud() {
        val collection = this.statusEffects
        if (!collection.isEmpty()) {
            val areaEffectCloudEntity = AreaEffectCloudEntity(world, this.x, this.y, this.z)
            areaEffectCloudEntity.radius = 2.5f
            areaEffectCloudEntity.radiusOnUse = -0.5f
            areaEffectCloudEntity.waitTime = 10
            areaEffectCloudEntity.duration = areaEffectCloudEntity.duration / 2
            areaEffectCloudEntity.radiusGrowth = -areaEffectCloudEntity.radius /
                areaEffectCloudEntity.duration
                    .toFloat()
            val var3: Iterator<*> = collection.iterator()
            while (var3.hasNext()) {
                val statusEffectInstance = var3.next() as StatusEffectInstance
                areaEffectCloudEntity.addEffect(StatusEffectInstance(statusEffectInstance))
            }
            world.spawnEntity(areaEffectCloudEntity)
        }
    }

    private val ignited: Boolean
        get() = dataTracker.get(ignitedTracker) as Boolean

    override fun ignite() {
        dataTracker.set(ignitedTracker, true)
    }

    companion object {
        private var fuseSpeedTracker: TrackedData<Int>? = null
        private var chargedTracker: TrackedData<Boolean>? = null
        private var ignitedTracker: TrackedData<Boolean>? = null

        fun createAttributes(): DefaultAttributeContainer.Builder = createHostileAttributes().add(EntityAttributes.MOVEMENT_SPEED, 0.25)

        init {
            fuseSpeedTracker = DataTracker.registerData(CreamerEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
            chargedTracker = DataTracker.registerData(CreamerEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
            ignitedTracker = DataTracker.registerData(CreamerEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
        }
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = animatableInstanceCache

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(
            DefaultAnimations.genericWalkIdleController(this),
        )
    }
}
