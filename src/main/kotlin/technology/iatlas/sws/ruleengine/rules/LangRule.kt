package technology.iatlas.sws.ruleengine.rules

import technology.iatlas.sws.ServerWebScript
import java.io.File

class LangRule: BaseRule("Server_LANG") {
    override fun proceed(sws: ServerWebScript, file: File) {
        super.proceed(sws, file)
        val regexRule = Regex("((s|S)(erver|ERVER)_(l|L)(ang|ANG))")

        file.forEachLine {
            if(it.contains(regexRule)) {
                sws.serverLang = it.split(":")[1].trim()
                logger.debug("SERVER_LANG: ${sws.serverLang}")
            }
        }
    }
}