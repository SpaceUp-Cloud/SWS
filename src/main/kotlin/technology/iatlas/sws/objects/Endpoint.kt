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

package technology.iatlas.sws.objects

import org.apache.logging.log4j.kotlin.Logging

data class Endpoint(val httpAction: String, val url: String) : Logging {
    private val urlParams: MutableMap<String, Any> = mutableMapOf()

    fun getUrlParams(): MutableMap<String, Any> {
        return urlParams
    }

    fun setUrlParams(key: String, value: Any?) {
        val templateParams = getTemplateUrlParams()

        // Has key and value
        if(templateParams.containsKey(key) && value != null) {
            urlParams[key] = value
            // Has key but no value but default value
        } else if(templateParams.containsKey(key) && value == null&& templateParams[key] != null) {
            urlParams[key] = templateParams[key] as Any
            // Has key but no possible value
        } else if(templateParams.containsKey(key) && value == null && templateParams[key] == null){
            logger.warn("Key '$key' was found but no value to map. " +
                    "Script variable '$key' won't be generated.")
        } else if(!templateParams.containsKey(key)) {
            logger.warn("Key '$key' not found. Won't map key '$key'.")
        }
    }

    fun setUrlParams(params: MutableMap<String, Any?>) {
        if(params.isNotEmpty()) {
            params.forEach { (t, u) -> setUrlParams(t, u)  }
        }
    }

    private fun getTemplateUrlParams(): Map<String, Any> {
        val params = mutableMapOf<String, Any>()
        val result = Regex("([^&?]+?)=([^&?]+)?", RegexOption.MULTILINE).findAll(url)

        result.iterator().forEach {
            params[it.groupValues[1]] = it.groupValues[2]
        }
        return params
    }
}