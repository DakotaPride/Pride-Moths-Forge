package net.dakotapride.pridemoths.block;

import net.dakotapride.pridemoths.PrideMothsMod;
import net.dakotapride.pridemoths.register.BlockEntityTypeRegistrar;
import net.dakotapride.pridemoths.register.DataComponentsRegistrar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

public class MothEnclosureBlockEntity extends BlockEntity implements Container, Nameable {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    private int lastInteractedSlot = -1;
    @Nullable
    private Component customName;

    public MothEnclosureBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistrar.MOTH_ENCLOSURE.get(), pos, state);
    }

    public static int getMothFuzzLevel(BlockState state) {
        return state.getValue(MothEnclosureBlock.FUZZ_LEVEL);
    }

    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }

    private void updateState(int interactedSlot) {
        if (interactedSlot >= 0 && interactedSlot < 3) {
            this.lastInteractedSlot = interactedSlot;
            BlockState blockState = this.getBlockState();

            for (int i = 0; i < MothEnclosureBlock.SLOT_OCCUPIED_PROPERTIES.size(); i++) {
                boolean bl = !this.getItem(i).isEmpty();
                BooleanProperty booleanProperty = MothEnclosureBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
                blockState = blockState.setValue(booleanProperty, bl);
            }

            (Objects.requireNonNull(this.getLevel())).setBlock(this.getBlockPos(), blockState, Block.UPDATE_ALL);
            this.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(blockState));
        }
    }

    public void addStack(ItemStack stack) {
        this.inventory.add(stack);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        //super.loadAdditional(nbt, registryLookup);
        //this.inventory.clear();
//        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
//        if (nbt.contains("Items", Tag.TAG_LIST)) {
//            ContainerHelper.loadAllItems(nbt, this.inventory, registryLookup);
//        }
//        this.lastInteractedSlot = nbt.getInt("last_interacted_slot");
//        if (nbt.contains("CustomName", Tag.TAG_STRING)) {
//            this.customName = parseCustomNameSafe(nbt.getString("CustomName"), registryLookup);
//        }



        super.loadAdditional(nbt, registryLookup);
        //this.inventory.clear();
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.getInventory().isEmpty()) {
            ContainerHelper.loadAllItems(nbt, this.inventory, registryLookup);
        }

        this.lastInteractedSlot = nbt.getIntOr("last_interacted_slot", -1);
        if (nbt.contains("CustomName")) {
            //this.customName = tryParseCustomName(nbt.getString("CustomName"), registryLookup);
            this.customName = parseCustomNameSafe(nbt.get("CustomName"), registryLookup);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
        ContainerHelper.saveAllItems(nbt, this.inventory, false, registryLookup);
        nbt.putInt("last_interacted_slot", this.lastInteractedSlot);
        if (this.hasCustomName()) {
            nbt.putString("CustomName", Component.Serializer.toJson(this.customName, registryLookup));
        }
    }

    public int getFilledSlotCount() {
        return (int)this.inventory.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }


    @Override
    public boolean isEmpty() {
        return this.inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack itemStack = Objects.requireNonNullElse(this.inventory.get(slot), ItemStack.EMPTY);
        this.inventory.set(slot, ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            this.updateState(slot);
        }

        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return this.removeItem(slot, 1);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (stack.is(PrideMothsMod.MOTH_JARS)) {
            this.inventory.set(slot, stack);
            this.updateState(slot);
        } else if (stack.isEmpty()) {
            this.removeItem(slot, 1);
        }
    }

    @Override
    public boolean canTakeItem(Container hopperInventory, int slot, ItemStack stack) {
        return hopperInventory.hasAnyMatching(
                stack2 -> stack2.isEmpty() || ItemStack.isSameItemSameComponents(stack, stack2) && stack2.getCount() + stack.getCount() <= hopperInventory.getMaxStackSize(stack2)
        );
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return stack.is(PrideMothsMod.MOTH_JARS) && this.getItem(slot).isEmpty() && stack.getCount() == this.getMaxStackSize();
    }

    public int getLastInteractedSlot() {
        return this.lastInteractedSlot;
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {}

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.customName = components.get(DataComponents.CUSTOM_NAME);
        components.getOrDefault(DataComponentsRegistrar.MOTH_CONTAINER, ItemContainerContents.EMPTY).copyInto(this.inventory);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder componentMapBuilder) {
        super.collectImplicitComponents(componentMapBuilder);
        componentMapBuilder.set(DataComponents.CUSTOM_NAME, this.customName);
        componentMapBuilder.set(DataComponentsRegistrar.MOTH_CONTAINER, ItemContainerContents.fromItems(this.inventory));
    }

    @Override
    public void removeComponentsFromTag(CompoundTag nbt) {
        nbt.remove("CustomName");
        nbt.remove("Items");
    }

//    @Nullable
//    @Override
//    public Packet<ClientPlayPacketListener> toUpdatePacket() {
//        return BlockEntityUpdateS2CPacket.create(this);
//    }

//    @Override
//    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
//        return createNbt(registryLookup);
//    }


    @Override
    public Component getName() {
        return this.customName != null ? this.customName : this.getDisplayName();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Moth Enclosure");
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.customName;
    }
}
