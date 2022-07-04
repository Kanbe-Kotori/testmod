package cn.nulladev.testmod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Random;

public class EntityMagicBall extends EntityFlying {

    private static final EntityDataAccessor<Integer> MAGIC_TYPE = SynchedEntityData.defineId(EntityHasOwner.class, EntityDataSerializers.INT);

    public EntityMagicBall(EntityType<?> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MAGIC_TYPE, new Random().nextInt(7));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(MAGIC_TYPE, nbt.getInt("magic_type"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("magic_type", this.entityData.get(MAGIC_TYPE));
    }

    public int getColor() {
        switch (this.entityData.get(MAGIC_TYPE)) {
            case 0: return 0x7FFF0000;
            case 1: return 0x7FFF7F00;
            case 2: return 0x7FFFFF00;
            case 3: return 0x7F00FF00;
            case 4: return 0x7F00FFFF;
            case 5: return 0x7F0000FF;
            case 6: return 0x7F7F00FF;
            default: return 0x00000000;
        }
    }

    @Override
    protected float getGravity() {
        return 0.03F;
    }

    @Override
    protected float getVDecrRate() {
        return 0.98F;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (this.getOwner() instanceof Player player) {
            entity.hurt(DamageSource.playerAttack(player), 3F);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {

    }

}
