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

package technology.iatlas.sws.templateengine

import technology.iatlas.sws.ServerWebScript
import java.util.*

class Bash: BaseEngine("bash.sh") {
    override fun generate(sws: ServerWebScript, userServerScript: String, params: Map<String, Any>): ServerWebScript {
        val preParsedSWS = super.generate(sws, userServerScript, params)

        // Add input parameters
        var parameters = ""
        params.keys.forEach {
            parameters += "$it=${params[it]}\n"
        }

        preParsedSWS.serverScript = this.templateFile
            .replace("%(${TemplateKeywords.DATE.name})", Date().toString(), ignoreCase = true)
            .replace("%(${TemplateKeywords.HTTP_ACTION.name})", sws.serverEndpoint.httpAction, ignoreCase = true)
            .replace("%(${TemplateKeywords.ENDPOINT.name})", sws.serverEndpoint.url, ignoreCase = true)
            .replace("%(${TemplateKeywords.SERVER_SCRIPT.name})", userServerScript, ignoreCase = true)
            .replace("%(${TemplateKeywords.PARAMS.name})", parameters, ignoreCase = true)

        return preParsedSWS
    }
}