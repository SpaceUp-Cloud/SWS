package technology.iatlas.sws.ruleengine.rules

import technology.iatlas.sws.ServerWebScript
import technology.iatlas.sws.objects.ParserException
import java.io.File

class SwaggerRule : BaseRule("SWAGGER_RULE") {

    override fun process(sws: ServerWebScript, swsFile: File): ServerWebScript {
        super.process(sws, swsFile)
        val regexRule = Regex("(SWAGGER_DOC:).+(\"{3})((.|\\s)*?)(?=\"{3})")

        val result = regexRule.find(swsFile.readText())?.groupValues
        if (result != null) {
            sws.swaggerDoc = result[3].trim()
        } else {
            throw ParserException("Could not parse SWAGGER_DOC!")
        }
        return sws
    }
}