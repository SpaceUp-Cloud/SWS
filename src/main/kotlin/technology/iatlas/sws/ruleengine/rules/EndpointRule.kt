/*
 * Copyright(c) 2022 spaceup@iatlas.technology.
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
import technology.iatlas.sws.objects.Endpoint
import technology.iatlas.sws.objects.ParserException
import java.io.File

class EndpointRule(private val urlParams: MutableMap<String, Any?>): BaseRule("SERVER_ENDPOINT") {

    override fun process(sws: SWS, swsFile: File, parse: (sws: File) -> SWS): SWS {
        return super.process(sws, swsFile) {
            val regexRule = Regex("${this.rule}:(.+)?([A-Z]*)(.*)?(.*)")
            val result = regexRule.find(it.readText())?.groupValues
            if (result != null) {
                val splittedEndpoint = result[1].trimStart().split(" ")
                val endpoint = Endpoint(splittedEndpoint[0], splittedEndpoint[1])
                endpoint.setUrlParams(urlParams)
                sws.serverEndpoint = endpoint
            } else {
                throw ParserException("Could not parse $rule!")
            }
            sws
        }
    }
}
