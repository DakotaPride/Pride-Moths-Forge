package net.dakotapride.pridemoths.register;

import com.mojang.serialization.Codec;
import net.dakotapride.pridemoths.PrideMothsMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class DataComponentsRegistrar {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, PrideMothsMod.ID);
//    public static final ComponentType<List<MothEnclosureBlockEntity.MothData>> MOTHS = register(
//            "moths",
//            builder -> builder.codec(MothEnclosureBlockEntity.MothData.LIST_CODEC).packetCodec(MothEnclosureBlockEntity.MothData.PACKET_CODEC.collect(PacketCodecs.toList())).cache()
//    );
public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> MOTH_CONTAINER = register("container",
        builder -> builder.persistent(ItemContainerContents.CODEC)
                .networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> CONTAINS_BABY = register("baby",
            builder -> builder.persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SAVED_AGE = register("age",
            builder -> builder.persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT).cacheEncoding());

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
//        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
//                ResourceLocation.fromNamespaceAndPath(PrideMothsMod.ID, id), builderOperator.apply(DataComponentType.builder()).build());
        return DATA_COMPONENT_TYPES.register(id, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void yep(IEventBus bus) {
        DATA_COMPONENT_TYPES.register(bus);
    }
}
