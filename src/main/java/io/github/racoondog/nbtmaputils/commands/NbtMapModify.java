package io.github.racoondog.nbtmaputils.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.Command;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

@Environment(EnvType.CLIENT)
public class NbtMapModify extends Command {
    public NbtMapModify() {
        super("nbtmap", "Modify a map item's nbt.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("increment").executes(ctx -> {
            modify(1);
            return SINGLE_SUCCESS;
        }));

        builder.then(literal("decrement").executes(ctx -> {
            modify(-1);
            return SINGLE_SUCCESS;
        }));

        builder.then(literal("modify").then(argument("int", IntegerArgumentType.integer()).executes(ctx -> {
            modify(IntegerArgumentType.getInteger(ctx, "int"));
            return SINGLE_SUCCESS;
        })));
    }

    private void modify(int number) {
        info("Modifying nbt by " + String.valueOf(number));
        ItemStack stack = mc.player.getInventory().getMainHandStack();

        if (stack == null) {
            error("You must hold an item in your main hand!");
            return;
        }
        if (!stack.isOf(Items.FILLED_MAP) && !stack.isOf(Items.MAP)) {
            error("You must hold a map in your main hand!");
            return;
        }

        NbtCompound tag = stack.getOrCreateNbt();
        if (!tag.contains("map")) {
            error("Map does not contain 'map' nbt element!");
            return;
        }

        int map = tag.getInt("map");
        map += number;
        tag.putInt("map", map);

        stack.setNbt(tag); //Dont know if required
    }
}
