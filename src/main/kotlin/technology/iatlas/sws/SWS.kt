/*
 * Copyright(c) 2022 thraax.session@gino-atlas.de.
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

import mu.KotlinLogging
import technology.iatlas.sws.objects.Endpoint
import technology.iatlas.sws.ruleengine.Parser
import technology.iatlas.sws.ruleengine.rules.*
import java.io.File

data class SWS(
    override var name: String = "",
    override var hoster: String = "",
    override var swaggerDoc: String = "",
    override var serverLang: String = "",
    override var clientLang: String = "",
    override var serverEndpoint: Endpoint = Endpoint("", ""),
    override var serverScript: String = "",
    override var serverResponseObjects: List<Any> = listOf(),
    //override var clientResponse: String, TODO implement me
    override var urlParams: MutableMap<String, Any?> = mutableMapOf(),
    override var httpBody: MutableMap<String, Any?> = mutableMapOf()
) : ServerWebScript {
    private val logger = KotlinLogging.logger {}

    override fun parse(file: File): SWS {
        logger.info("Called parser")
        Parser(this, file)
            .addRule(NameRule())
            .addRule(SwaggerRule())
            .addRule(HosterRule())
            .addRule(EndpointRule(urlParams))
            .addRule(LangRule())
            .addRule(ServerScriptRule(httpBody))
            .parse()

        return this
    }

    override fun parse(file: File, rules: List<Rule>): SWS {
        logger.info("Called parser")
        val parser = Parser(this, file)

        rules.map {
            parser.addRule(it)
        }.apply { parser.parse() }

        return this
    }

    companion object {
        val endpoint = Endpoint("", "")

        fun createAndParse(file: File,
                           urlParams: MutableMap<String, Any?> = mutableMapOf(),
                           httpBody: MutableMap<String, Any?> = mutableMapOf()
        ): SWS {
            val sws = SWS(
                "",
                "Uberspace", "", "", "",
                endpoint, "", arrayListOf(), urlParams, httpBody
            )

            sws.parse(file)
            return sws
        }

        fun createAndParse(
            file: File, rules: List<Rule>,
            urlParams: MutableMap<String, Any?> = mutableMapOf(),
            httpBody: MutableMap<String, Any?> = mutableMapOf()
        ): SWS {
            val sws = SWS(
                "",
                "Uberspace", "", "", "",
                endpoint, "", arrayListOf(), urlParams, httpBody
            )

            sws.parse(file, rules)
            return sws
        }
    }
}