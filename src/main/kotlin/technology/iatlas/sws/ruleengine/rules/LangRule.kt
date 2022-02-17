package technology.iatlas.sws.ruleengine.rules

import technology.iatlas.sws.ServerWebScript
import technology.iatlas.sws.objects.ParserException
import java.io.File

class LangRule : BaseRule("SERVER_LANG") {

    override fun process(sws: ServerWebScript, swsFile: File): ServerWebScript {
        super.process(sws, swsFile)
        val regexRule = Regex("SERVER_LANG:(.+)?(.*)")

        val result = regexRule.find(swsFile.readText())?.groupValues
        if(result != null) {
            sws.serverLang = result[1].trimStart()
            logger.debug("SERVER_LANG: ${sws.serverLang}")
        } else {
            throw ParserException("Could not parse SERVER_LANG!")
        }
        return sws
    }
}