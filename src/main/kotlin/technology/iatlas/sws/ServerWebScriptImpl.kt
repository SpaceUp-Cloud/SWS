/*
 * Copyright(c) 2021 thraax.session@gino-atlas.de.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package technology.iatlas.sws

import org.apache.logging.log4j.kotlin.Logging
import technology.iatlas.sws.objects.Endpoint
import technology.iatlas.sws.ruleengine.Parser
import technology.iatlas.sws.ruleengine.rules.HosterRule
import java.io.File

/**
 * ServerWebScript is the most important object which presents itself
 */
class ServerWebScriptImpl(var file: File,
                                   override var _metaComments: List<String>,
                                   override var hoster: String,
                                   override var swaggerDoc: String,
                                   override var serverLang: String,
                                   override var clientLang: String,
                                   override var serverEndpoint: Endpoint,
                                   override var serverScript: String,
                                   override var serverResponseObjects: List<Any>,
                                   override var clientResponse: String
) : ServerWebScript, Logging {
    private lateinit var sws: ServerWebScript

    override fun parse() {
        logger.info("Called parser")
        val parser = Parser(this, file)
        parser
            .addRule(HosterRule())
        parser.parse()

        sws = this
    }

    companion object { }
}

internal fun ServerWebScriptImpl.Companion.createAndParse(file: File): ServerWebScript {
    val sws = ServerWebScriptImpl(
        file,
        arrayListOf(),
        "Uberspace", "", "", "",
        Endpoint("", ""), "", arrayListOf(), ""
    )

    sws.parse()
    return sws
}

internal fun ServerWebScriptImpl.Companion.create(file: File): ServerWebScript {
    return ServerWebScriptImpl(
        file,
        arrayListOf(),
        "Uberspace", "", "", "",
        Endpoint("", ""), "", arrayListOf(), ""
    )
}

object SWSCreator {
    fun createAndParse(file: File): ServerWebScript {
        return ServerWebScriptImpl.createAndParse(file)
    }

    fun create(file: File): ServerWebScript {
        return ServerWebScriptImpl.create(file)
    }
}