package io.github.racoondog.nbtmaputils;

import io.github.racoondog.nbtmaputils.commands.NbtMapModify;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;

@Environment(EnvType.CLIENT)
public class NbtMapUtils extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        // Commands
        Commands.get().add(new NbtMapModify());
    }

    @Override
    public String getPackage() {
        return "io.github.racoondog.nbtmaputils";
    }
}
