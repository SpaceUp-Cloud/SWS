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

package technology.iatlas.sws.ruleengine.rules

import technology.iatlas.sws.SWS
import technology.iatlas.sws.objects.ParserException
import technology.iatlas.sws.templateengine.Template
import java.io.File

class ServerScriptRule(
    private val httpBody: MutableMap<String, Any?> = mutableMapOf()
) : BaseRule("SERVER_SCRIPT") {

    override fun process(sws: SWS, swsFile: File, parse: (sws: File) -> SWS): SWS {
        return super.process(sws, swsFile) {
            val urlParams = sws.serverEndpoint.getUrlParams()
            val regexRule = Regex("(${this.rule}:).+(\"{3})((.|\\s)*?)(?=\"{3})")

            val result = regexRule.find(it.readText())?.groupValues
            if(result != null) {
                val sst = result[3].trim()
                Template.generate(sws, sst, urlParams, httpBody)
            } else {
                throw ParserException("Could not parse ${this.rule}!")
            }
        }

    }
}