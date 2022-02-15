package technology.iatlas.sws.ruleengine.rules

import technology.iatlas.sws.ServerWebScript
import java.io.File

class LangRule: BaseRule("SERVER_LANG") {
    override fun process(sws: ServerWebScript, rawfile: File) {
        super.process(sws, rawfile)
        val regexRule = Regex("(server|SERVER|Server)_(lang|LANG|Lang)")

        this.baseFile.forEachLine {
            if(it.contains(regexRule)) {
                sws.serverLang = it.split(":")[1].trim()
                logger.debug("SERVER_LANG: ${sws.serverLang}")
            }
        }
    }
}