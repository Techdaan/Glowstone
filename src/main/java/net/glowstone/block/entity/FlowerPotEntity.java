package net.glowstone.block.entity;

import lombok.Getter;
import lombok.Setter;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.block.entity.state.GlowFlowerPot;
import net.glowstone.constants.GlowBlockEntity;
import net.glowstone.constants.ItemIds;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.util.nbt.CompoundTag;
import org.bukkit.material.MaterialData;

public class FlowerPotEntity extends BlockEntity {
    @Getter
    @Setter
    private MaterialData contents;

    public FlowerPotEntity(GlowBlock block) {
        super(block);
        setSaveId("minecraft:flower_pot");
    }

    @Override
    public void loadNbt(CompoundTag tag) {
        super.loadNbt(tag);
        tag.tryGetMaterial("Item").ifPresent(
            item -> this.contents = item.getNewData(
                    (byte) (int) (tag.tryGetInt("Data").orElse(0))));
    }

    @Override
    public void saveNbt(CompoundTag tag) {
        super.saveNbt(tag);

        if (contents != null) {
            tag.putString("Item", ItemIds.getName(contents.getItemType()));
            tag.putInt("Data", contents.getData());
        } else {
            // Mimics how Minecraft does it.
            tag.putString("Item", "");
            tag.putInt("Data", 0);
        }
    }

    @Override
    public GlowBlockState getState() {
        return new GlowFlowerPot(block);
    }

    @Override
    public void update(GlowPlayer player) {
        super.update(player);

        CompoundTag nbt = new CompoundTag();

        saveNbt(nbt);
        player.sendBlockEntityChange(getBlock().getLocation(), GlowBlockEntity.FLOWER_POT, nbt);
    }
}
