package technology.iatlas.sws.ruleengine.rules

import technology.iatlas.sws.ServerWebScript
import technology.iatlas.sws.objects.ParserException
import java.io.File

class LangRule : BaseRule("SERVER_LANG") {

    override fun process(sws: ServerWebScript, swsFile: File, parse: (sws: File) -> ServerWebScript): ServerWebScript {
        return super.process(sws, swsFile) {
            val regexRule = Regex("${this.rule}:(.+)?(.*)")

            val result = regexRule.find(it.readText())?.groupValues
            if(result != null) {
                sws.serverLang = result[1].trimStart()
                logger.debug("SERVER_LANG: ${sws.serverLang}")
            } else {
                throw ParserException("Could not parse SERVER_LANG!")
            }
            sws
        }

    }
}