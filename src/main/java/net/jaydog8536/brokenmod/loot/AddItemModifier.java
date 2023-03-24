package net.jaydog8536.brokenmod.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.jaydog8536.brokenmod.BrokenMod;
import net.jaydog8536.brokenmod.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Supplier;

public class AddItemModifier {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BrokenMod.MOD_ID);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BROKENMOD_DROPS = GLM.register("brokenmod_drops", BlockDropModifier.CODEC);

    public static class BlockDropModifier extends LootModifier {
        public static final Supplier<Codec<BlockDropModifier>> CODEC = Suppliers.memoize(() ->
                RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, BlockDropModifier::new)));

        public BlockDropModifier(LootItemCondition[] lootConditions) {
            super(lootConditions);
        }

        @Nonnull
        @Override
        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            if (context.hasParam(LootContextParams.BLOCK_STATE)) {
                BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
                Block block = state.getBlock();
                if (block == Blocks.OAK_LOG||
                        block == Blocks.ACACIA_LOG||
                        block == Blocks.SPRUCE_LOG||
                        block == Blocks.DARK_OAK_LOG||
                        block == Blocks.JUNGLE_LOG||
                        block == Blocks.BIRCH_LOG||
                        block == Blocks.STRIPPED_OAK_LOG||
                        block == Blocks.STRIPPED_ACACIA_LOG||
                        block == Blocks.STRIPPED_SPRUCE_LOG||
                        block == Blocks.STRIPPED_DARK_OAK_LOG||
                        block == Blocks.STRIPPED_JUNGLE_LOG||
                        block == Blocks.STRIPPED_BIRCH_LOG) {
                    Random rand = new Random();
                    int chance = rand.nextInt(100);
                    // 1/5 chance
                    if(chance <= 20) {
                        generatedLoot.clear();
                        generatedLoot.add(new ItemStack(ModItems.WOOD_CHIP.get(), 1));
                    }
                }
            }
            return generatedLoot;
        }

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
            return BROKENMOD_DROPS.get();
        }
    }

}
