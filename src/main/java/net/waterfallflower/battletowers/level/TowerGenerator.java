package net.waterfallflower.battletowers.level;

import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.structure.Structure;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.waterfallflower.battletowers.entity.GolemMob;

import java.util.Random;

public class TowerGenerator extends Structure {

    @Override
    public boolean generate(Level world, Random random, int i, int j, int k) {
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        boolean flag1 = false;
        for (int k1 = 0; k1 < 32; k1++) {
            int l1 = i + random.nextInt(8) - random.nextInt(8);
            int i2 = j + random.nextInt(4) - random.nextInt(4);
            int j2 = k + random.nextInt(8) - random.nextInt(8);
            l = l1;
            i1 = i2;
            j1 = j2;
            boolean flag5 = false;
            if ((world.getTileId(l1, i2, j2) == 0 || world.getTileId(l1, i2, j2) == BlockBase.SNOW.id) && world.getTileId(l1, i2 - 1, j2) != 0) {
                int i3 = -4;
                while (i3 < 5) {
                    int i4 = -4;
                    while (i4 < 5) {
                        int l4 = world.getTileId(l1 + i3, i2, j2 + i4);
                        if (l4 != 0 && l4 != BlockBase.SNOW.id && l4 != (BlockBase.BY_ID[37]).id && l4 != (BlockBase.BY_ID[38]).id && l4 != (BlockBase.BY_ID[17]).id)
                            flag5 = true;
                        int l5 = world.getTileId(l1 + i3, i2 - 1, j2 + i4);
                        if (l5 != BlockBase.GRASS.id && l5 != BlockBase.SAND.id && l5 != BlockBase.STONE.id)
                            flag5 = true;
                        if (flag5)
                            break;
                        i4++;
                    }
                    if (flag5)
                        break;
                    i3++;
                }
                if (!flag5) {
                    flag1 = true;
                    break;
                }
            }
        }
        if (!flag1)
            return false;
        int k2 = i1 - 6;
        int l2 = random.nextInt(3);
        int floor_int = 1;
        this.top_floor_int = 0;
        for (; k2 < 120; k2 += 7) {
            if (k2 + 7 >= 120)
                this.top_floor_int = 1;
            for (int j3 = 0; j3 < 7; j3++) {
                if (k2 == i1 - 6 && j3 < 4)
                    j3 = 4;
                for (int j4 = -7; j4 < 7; j4++) {
                    for (int i5 = -7; i5 < 7; i5++) {
                        int i6 = j4 + l;
                        int k6 = j3 + k2;
                        int l6 = i5 + j1;
                        if (i5 == -7) {
                            if (j4 > -5 && j4 < 4)
                                world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                        } else if (i5 == -6 || i5 == -5) {
                            if (j4 == -5 || j4 == 4) {
                                world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                            } else if (i5 == -6) {
                                if (j4 == (j3 + 1) % 7 - 3) {
                                    world.setTile(i6, k6, l6, BlockBase.DOUBLE_STONE_SLAB.id);
                                    if (j3 == 5)
                                        world.setTile(i6 - 7, k6, l6, BlockBase.DOUBLE_STONE_SLAB.id);
                                    if (j3 == 6 && this.top_floor_int == 1)
                                        world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                                } else if (j4 < 4 && j4 > -5) {
                                    world.setTile(i6, k6, l6, 0);
                                }
                            } else if (i5 == -5 && j4 > -5 && j4 < 5) {
                                if ((j3 != 0 && j3 != 6) || (j4 != -4 && j4 != 3)) {
                                    if (j3 == 5 && (j4 == 3 || j4 == -4)) {
                                        world.setTile(i6, k6, l6, BlockBase.DOUBLE_STONE_SLAB.id);
                                    } else {
                                        world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                                    }
                                } else {
                                    world.setTile(i6, k6, l6, 0);
                                }
                            }
                        } else if (i5 == -4 || i5 == -3 || i5 == 2 || i5 == 3) {
                            if (j4 == -6 || j4 == 5) {
                                world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                            } else if (j4 > -6 && j4 < 5) {
                                if (j3 == 5) {
                                    world.setTile(i6, k6, l6, BlockBase.DOUBLE_STONE_SLAB.id);
                                } else if (world.getTileId(i6, k6, l6) != 54) {
                                    world.setTile(i6, k6, l6, 0);
                                }
                            }
                        } else if (i5 > -3 && i5 < 2) {
                            if (j4 == -7 || j4 == 6) {
                                if (j3 < 0 || j3 > 3 || (j4 != -7 && j4 != 6) || (i5 != -1 && i5 != 0)) {
                                    world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                                } else {
                                    world.setTile(i6, k6, l6, 0);
                                }
                            } else if (j4 > -7 && j4 < 6) {
                                if (j3 == 5) {
                                    world.setTile(i6, k6, l6, BlockBase.DOUBLE_STONE_SLAB.id);
                                } else {
                                    world.setTile(i6, k6, l6, 0);
                                }
                            }
                        } else if (i5 == 4) {
                            if (j4 == -5 || j4 == 4) {
                                world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                            } else if (j4 > -5 && j4 < 4) {
                                if (j3 == 5) {
                                    world.setTile(i6, k6, l6, BlockBase.DOUBLE_STONE_SLAB.id);
                                } else {
                                    world.setTile(i6, k6, l6, 0);
                                }
                            }
                        } else if (i5 == 5) {
                            if (j4 == -4 || j4 == -3 || j4 == 2 || j4 == 3) {
                                world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                            } else if (j4 > -3 && j4 < 2) {
                                if (j3 == 5) {
                                    world.setTile(i6, k6, l6, BlockBase.DOUBLE_STONE_SLAB.id);
                                } else {
                                    world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                                }
                            }
                        } else if (i5 == 6 && j4 > -3 && j4 < 2) {
                            if (j3 < 0 || j3 > 3 || (j4 != -1 && j4 != 0)) {
                                world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                            } else {
                                world.setTile(i6, k6, l6, func_22235_func_20337_cobbler(l2, random));
                            }
                        }
                    }
                }
            }
            if (floor_int == 2) {
                world.setTile(l + 3, k2, j1 - 5, func_22235_func_20337_cobbler(l2, random));
                world.setTile(l + 3, k2 - 1, j1 - 5, func_22235_func_20337_cobbler(l2, random));
            }
            if (this.top_floor_int == 1) {
                double d1 = (k2 + 6);
                double d2 = j1 + 0.5D;
                GolemMob entitygolem = new GolemMob(world, l2);
                entitygolem.setPositionAndAngles(l, d1, d2, world.rand.nextFloat() * 360.0F, 0.0F);
                world.spawnEntity(entitygolem);
            } else {
                world.setTile(l + 2, k2 + 6, j1 + 2, BlockBase.MOB_SPAWNER.id);
                TileEntityMobSpawner spawner_1 = (TileEntityMobSpawner)world.getTileEntity(l + 2, k2 + 6, j1 + 2);
                spawner_1.setEntityId(func_22234_b(random));
                world.setTile(l - 3, k2 + 6, j1 + 2, BlockBase.MOB_SPAWNER.id);
                TileEntityMobSpawner spawner_2 = (TileEntityMobSpawner)world.getTileEntity(l - 3, k2 + 6, j1 + 2);
                spawner_2.setEntityId(func_22234_b(random));
            }
            world.setTile(l, k2 + 6, j1 - 3, BlockBase.DOUBLE_STONE_SLAB.id);
            world.setTile(l - 1, k2 + 6, j1 - 3, BlockBase.DOUBLE_STONE_SLAB.id);
            if (k2 + 56 >= 120 && floor_int == 1)
                floor_int = 2;
            for (int k3 = 0; k3 < 2; k3++) {
                world.setTile(l - k3, k2 + 7, j1 - 3, 54);
                TileEntityChest tileentitychest = new TileEntityChest();
                world.setTileEntity(l - k3, k2 + 7, j1 - 3, tileentitychest);
                for (int j5 = 0; j5 < 1 + k3 + l2; j5++) {
                    ItemInstance itemstack =
                            func_22233_func_20339_a(floor_int, random);
                    if (itemstack != null)
                        tileentitychest.setInventoryItem(random.nextInt(tileentitychest.getInventorySize()), itemstack);
                }
            }
            world.setTile(l + 3, k2, j1 - 6, BlockBase.TORCH.id);
            world.setTile(l - 4, k2, j1 - 6, BlockBase.TORCH.id);
            world.setTile(l + 1, k2, j1 - 4, BlockBase.TORCH.id);
            world.setTile(l - 2, k2, j1 - 4, BlockBase.TORCH.id);
            for (int l3 = 0; l3 < floor_int * 4 + l2 - 8 && this.top_floor_int != 1; l3++) {
                int k4 = 5 - random.nextInt(12);
                int k5 = k2 + 5;
                int j6 = 5 - random.nextInt(10);
                if (j6 >= -2 || k4 >= 4 || k4 <= -5 || k4 == 1 || k4 == -2) {
                    k4 += l;
                    j6 += j1;
                    if (world.getTileId(k4, k5, j6) == BlockBase.DOUBLE_STONE_SLAB.id && world.getTileId(k4, k5 + 1, j6) != BlockBase.MOB_SPAWNER.id)
                        world.setTile(k4, k5, j6, 0);
                }
            }
            floor_int++;
        }
        return true;
    }

    private int func_22235_func_20337_cobbler(final int i, final Random random) {
        if (i == 0) {
            return BlockBase.COBBLESTONE.id;
        }
        if (i != 1) {
            return BlockBase.BY_ID[48].id;
        }
        if (random.nextInt(3) == 0) {
            return BlockBase.COBBLESTONE.id;
        }
        return BlockBase.BY_ID[48].id;
    }

    private String func_22234_b(Random random) {
        int i = random.nextInt(3);
        switch (i) {
            case 0:
                return "Skeleton";
            case 2:
                return "Spider";
            default:
                return "Zombie";
        }
    }

    private ItemInstance func_22233_func_20339_a(int i, Random random) {
        int j = random.nextInt(4);
        if (this.top_floor_int == 1) {
            if (j == 0)
                return new ItemInstance(ItemBase.ironSword, 1);
            if (j == 1)
                return new ItemInstance(ItemBase.ironPickaxe, 1);
            if (j == 2)
                return new ItemInstance(ItemBase.ironAxe, 1);
            return new ItemInstance(ItemBase.ironShovel, 1);
        }
        if (i == 1) {
            if (j == 0)
                return new ItemInstance(ItemBase.stick, random.nextInt(5) + 2);
            if (j == 1)
                return new ItemInstance(ItemBase.seeds, random.nextInt(5) + 2);
            if (j == 2)
                return new ItemInstance(BlockBase.BY_ID[4], random.nextInt(5) + 4);
            return new ItemInstance(BlockBase.BY_ID[12], random.nextInt(5) + 4);
        }
        if (i == 2) {
            if (j == 0)
                return new ItemInstance(ItemBase.coal, random.nextInt(3) + 6);
            if (j == 1)
                return new ItemInstance(ItemBase.woodPickaxe, 1);
            if (j == 2)
                return new ItemInstance(BlockBase.BY_ID[5], random.nextInt(3) + 4);
            return new ItemInstance(BlockBase.BY_ID[35], random.nextInt(3) + 6);
        }
        if (i == 3) {
            if (j == 0)
                return new ItemInstance(ItemBase.feather, random.nextInt(3) + 6);
            if (j == 1)
                return new ItemInstance(ItemBase.bread, 1);
            if (j == 2)
                return new ItemInstance(BlockBase.BY_ID[20], random.nextInt(3) + 5);
            return new ItemInstance(BlockBase.BY_ID[39], random.nextInt(3) + 3);
        }
        if (i == 4) {
            if (j == 0)
                return new ItemInstance(ItemBase.string, random.nextInt(3) + 2);
            if (j == 1)
                return new ItemInstance(ItemBase.stoneSword, 1);
            if (j == 2)
                return new ItemInstance(BlockBase.BY_ID[50], random.nextInt(3) + 5);
            return new ItemInstance(BlockBase.BY_ID[40], random.nextInt(3) + 3);
        }
        if (i == 5) {
            if (j == 0)
                return new ItemInstance(BlockBase.BY_ID[53], random.nextInt(3) + 5);
            if (j == 1)
                return new ItemInstance(ItemBase.bow, 1);
            if (j == 2)
                return new ItemInstance(BlockBase.BY_ID[45], random.nextInt(3) + 5);
            return new ItemInstance(ItemBase.chainBoots, 1);
        }
        if (i == 6) {
            if (j == 0)
                return new ItemInstance(BlockBase.BY_ID[65], random.nextInt(3) + 9);
            if (j == 1)
                return new ItemInstance(ItemBase.flintAndSteel, 1);
            if (j == 2)
                return new ItemInstance(BlockBase.BY_ID[89], random.nextInt(3) + 5);
            return new ItemInstance(ItemBase.chainHelmet, 1);
        }
        if (i == 7) {
            if (j == 0)
                return new ItemInstance(BlockBase.BY_ID[91], random.nextInt(3) + 9);
            if (j == 1)
                return new ItemInstance(ItemBase.lavaBucket, 1);
            if (j == 2)
                return new ItemInstance(BlockBase.BY_ID[66], random.nextInt(5) + 9);
            return new ItemInstance(ItemBase.chainLeggings, 1);
        }
        if (i == 8) {
            if (j == 0)
                return new ItemInstance(BlockBase.BY_ID[46], random.nextInt(3) + 3);
            if (j == 1)
                return new ItemInstance(ItemBase.diamondHoe, 1);
            if (j == 2)
                return new ItemInstance(BlockBase.BY_ID[49], random.nextInt(3) + 6);
            return new ItemInstance(ItemBase.chainChestplate, 1);
        }
        if (random.nextInt(4) == 0)
            return new ItemInstance(ItemBase.diamond, random.nextInt(3) + 3);
        if (random.nextInt(4) == 1)
            return new ItemInstance(ItemBase.ironIngot, random.nextInt(3) + 3);
        if (random.nextInt(4) == 2)
            return new ItemInstance(ItemBase.diamond, random.nextInt(4) + 6);
        if (random.nextInt(4) == 3)
            return new ItemInstance(ItemBase.ironIngot, random.nextInt(4) + 6);
        return null;
    }

    private int top_floor_int;
}
