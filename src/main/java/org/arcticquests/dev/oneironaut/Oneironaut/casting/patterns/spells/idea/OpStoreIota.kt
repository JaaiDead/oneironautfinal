package org.arcticquests.dev.oneironaut.Oneironaut.casting.patterns.spells.idea


import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapOthersName
import at.petrak.hexcasting.api.misc.MediaConstants
import org.arcticquests.dev.oneironaut.Oneironaut.casting.idea.IdeaEntry
import org.arcticquests.dev.oneironaut.Oneironaut.casting.idea.IdeaInscriptionManager

class OpStoreIota : ConstMediaAction {
    override val argc = 2
    override val mediaCost = MediaConstants.DUST_UNIT / 4
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val iotaToWrite = args[1]
        val truename = MishapOthersName.getTrueNameFromDatum(iotaToWrite, env.caster)
        if (truename != null){
            throw MishapOthersName(truename)
        }
        val ideaState = IdeaInscriptionManager.getServerState(env.world.server)
        val keyIota = args.getIdeaKey(0, argc, env)
        IdeaInscriptionManager.writeEntry(keyIota, IdeaEntry(iotaToWrite, env.world.gameTime, env.castingEntity))
        ideaState.markDirty()
        return listOf()
    }
}