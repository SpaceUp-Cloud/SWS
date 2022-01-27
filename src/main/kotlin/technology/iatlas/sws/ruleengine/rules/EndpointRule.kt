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

package technology.iatlas.sws.ruleengine.rules

import technology.iatlas.sws.ServerWebScript
import technology.iatlas.sws.objects.Endpoint
import java.io.File

class EndpointRule : BaseRule("SERVER_ENDPOINT") {
    override fun proceed(sws: ServerWebScript, file: File) {
        super.proceed(sws, file)
        val regexRule = Regex("(endpoint|ENDPOINT|Endpoint)")

        file.forEachLine {
            if(it.contains(regexRule)) {
                val splitted = it.split(":")[1].trim().split(" ")
                sws.serverEndpoint = Endpoint(splitted[0], splitted[1])
                logger.debug("ENDPOINT: ${sws.serverEndpoint}")
            }
        }
    }
}
