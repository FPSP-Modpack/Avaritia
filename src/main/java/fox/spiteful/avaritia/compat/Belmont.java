package fox.spiteful.avaritia.compat;

import net.minecraft.entity.Entity;

import com.emoniph.witchery.util.CreatureUtil;

import cpw.mods.fml.common.Loader;

public class Belmont {

    public static boolean isVampire(Entity entity) {

        if (Loader.isModLoaded("witchery")) {
            try {
                return CreatureUtil.isVampire(entity);
            } catch (Throwable e) {}
        }

        return false;

    }

}
