package com.theoparis.cw.item

import net.minecraft.advancement.criterion.Criteria
import net.minecraft.component.type.ConsumableComponents
import net.minecraft.component.type.FoodComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsage
import net.minecraft.item.Items
import net.minecraft.item.consume.UseAction
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.stat.Stats
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World

class CreamJarItem(
    settings: Settings,
) : Item(settings.food(FoodComponent.Builder().build(), ConsumableComponents.drink().build())) {
    override fun finishUsing(
        stack: ItemStack,
        world: World,
        user: LivingEntity,
    ): ItemStack {
        if (user is ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(user, stack)
            user.incrementStat(Stats.USED.getOrCreateStat(this))
        }

        if (user is PlayerEntity && !user.abilities.creativeMode) {
            stack.decrement(1)
        }

        if (!world.isClient) {
            user.clearStatusEffects()
        }

        return if (stack.isEmpty) ItemStack(Items.GLASS_BOTTLE) else stack
    }

    override fun getUseAction(stack: ItemStack): UseAction = UseAction.DRINK

    override fun use(
        world: World,
        user: PlayerEntity,
        hand: Hand,
    ): ActionResult = ItemUsage.consumeHeldItem(world, user, hand)
}
