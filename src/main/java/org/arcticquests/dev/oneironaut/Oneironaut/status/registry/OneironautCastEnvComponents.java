package org.arcticquests.dev.oneironaut.Oneironaut.status.registry;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import org.arcticquests.dev.oneironaut.Oneironaut.utils.WorldUtils;

public class OneironautCastEnvComponents {    public static void init(){
    CastingEnvironment.addCreateEventListener((env, nbt)->{
        if (env instanceof PlayerBasedCastEnv){
            if (WorldUtils.isWorldNoosphere(env.getWorld())){
                env.addExtension(new NoosphereAmbitExtensionComponent(env));
            }
        }
    });
}

}
