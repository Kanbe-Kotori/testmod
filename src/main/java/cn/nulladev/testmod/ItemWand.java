package cn.nulladev.testmod;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemWand extends Item {

    public ItemWand(Properties props) {
        super(props.stacksTo(1));
    }

    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        EntityMagic magic = new EntityMagic(Registry.MAGIC.get(), level);
        magic.setOwner(player);
        level.addFreshEntity(magic);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }
}
