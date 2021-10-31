package net.waterfallflower.battletowers.entity;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.monster.MonsterBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathHelper;

import java.util.List;

public class GolemMob extends MonsterBase {
    private int agressive_mode;
    private int pathToEntity;
    private int session_growl;
    private int drops;

    public GolemMob(Level arg, int i) {
        super(arg);
        this.texture = "/assets/battletowers/textures/mob/golemdormant.png";
        this.movementSpeed = 0.35f;
        super.attackDamage = 8;
        this.health = 150 + 150 * i;
        this.setSize(1.6f, 3.4f);
        this.yaw = 0.0f;
        this.agressive_mode = 1;
        this.pathToEntity = 0;
        this.session_growl = 0;
        this.immuneToFire = true;
        this.drops = 2 + 2 * i;
        this.setPositionAndAngles(this.x, this.y, this.z, 0.0f, 0.0f);
    }

    public GolemMob(Level arg) {
        this(arg, 1);
    }

    @Override
    public void remove() {
        if(this.health <= 0 || this.level.difficulty == 0) {
            super.remove();
        }
    }

    @Override
    public void onKilledBy(EntityBase arg) {
        if (this.field_1024 > 0 && entity != null)
            entity.onKilledOther(this, this.field_1024);

        if (!this.level.isClient) {
            for (int i = this.drops - this.rand.nextInt(2), j = 0; j < i; ++j)
                this.dropItem(ItemBase.diamond.id, 1);
            for (int i = this.rand.nextInt(4) + 9, k = 0; k < i; ++k)
                this.dropItem(BlockBase.STONE_SLAB.id, 1);
        }
        this.level.method_185(this, (byte)3);
    }

    @Override
    public void method_925(EntityBase arg, int i, double d, double d1) {
        this.movementSpeed = 0.35f + (float)((450 - this.health) / 1750.0);
        if (this.rand.nextInt(5) == 0) {
            this.velocityX *= 1.5;
            this.velocityZ *= 1.5;
            this.velocityY += 0.6000000238418579;
        }
        this.pathToEntity = 150;
    }

    protected void tickActivateGolem() {
        if (this.agressive_mode == 1) {
            PlayerBase entityplayer = this.level.getClosestPlayerTo(this, 6.0D);
            if (entityplayer != null && this.method_928(entityplayer)) {
                this.agressive_mode = 0;
                this.level.playSound(this.x, this.y, this.z, "ambient.cave.cave", 0.7F, 1.0F);
                this.level.playSound(this, "battletowers:golemawaken", getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
                this.texture = "/assets/battletowers/textures/mob/golem.png";
                this.pathToEntity = 175;
            }
        } else {
            List<EntityBase> list = this.level.getEntities(this, this.boundingBox.expand(6.0D, 6.0D, 6.0D));
            boolean flag = false;
            int i = 0;
            while (i < list.size()) {
                EntityBase entity1 = list.get(i);
                if (entity1 == this.entity) {
                    flag = true;
                    break;
                }
                i++;
            }
            if ((!flag && this.entity != null) || this.session_growl == 1) {
                this.pathToEntity--;
            } else {
                this.pathToEntity = 175;
            }
        }
    }

    @Override
    public void tick() {
        if (this.agressive_mode == 0) {
            if (this.pathToEntity <= 0 && this.session_growl == 0) {
                if (this.session_growl == 0 && this.entity instanceof PlayerBase && this.level.getClosestPlayerTo(this, 24.0) == null) {
                    this.entity = null;
                }
                else {
                    this.level.playSound(this, "battletowers:golemspecial", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
                    this.velocityY += 0.9;
                    this.session_growl = 1;
                }
            }
            else if ((this.pathToEntity <= -30 || this.onGround) && this.session_growl == 1) {
                if (this.health <= 425) {
                    this.health += 25;
                }
                this.level.createExplosion(this, this.x, this.y, this.z, 4.5f + (this.drops - 2) / 4.0f);
                this.pathToEntity = 125;
                this.session_growl = 0;
            }
            super.tick();
        }
        this.tickActivateGolem();
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("isDormant", (byte)this.agressive_mode);
        tag.put("hasGrowled", (byte)this.session_growl);
        tag.put("rageCounter", (byte)this.pathToEntity);
        tag.put("Drops", (byte)this.drops);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.agressive_mode = (tag.getByte("isDormant") & 0xFF);
        this.session_growl = (tag.getByte("hasGrowled") & 0xFF);
        this.pathToEntity = (tag.getByte("rageCounter") & 0xFF);
        this.drops = (tag.getByte("Drops") & 0xFF);
        this.movementSpeed = 0.35f + (float) ((450 - this.health) / 1750.0);
        this.texture = "/assets/battletowers/textures/mob/" + (this.agressive_mode == 1 ? "golemdormant.png" : "golem.png");
        super.attackDamage = 8;
    }

    @Override
    protected void tryAttack(EntityBase target, float f) {
        if (f < 3.0 && target.boundingBox.maxY > this.boundingBox.minY && target.boundingBox.minY < this.boundingBox.maxY)
            target.damage(this, super.attackDamage);

        if (this.onGround) {
            final double d = target.x - this.x;
            final double d2 = target.z - this.z;
            final float f2 = MathHelper.sqrt(d * d + d2 * d2);
            this.velocityX = d / f2 * 0.5 * 0.20000000192092895 + this.velocityX * 0.20000000098023224;
            this.velocityZ = d2 / f2 * 0.5 * 0.10000000192092896 + this.velocityZ * 0.20000000098023224;
        } else super.tryAttack(target, f);
    }

    @Override
    protected String getAmbientSound() {
        return this.agressive_mode == 0 ? "battletowers:golem" : null;
    }

    @Override
    protected String getHurtSound() {
        return "battletowers:golemhurt";
    }

    @Override
    protected String getDeathSound() {
        return "battletowers:golemdeath";
    }

    @Override
    protected int getMobDrops() {
        return ItemBase.brick.id;
    }
}
