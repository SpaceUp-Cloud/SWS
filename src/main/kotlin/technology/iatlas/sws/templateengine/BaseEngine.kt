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
import technology.iatlas.sws.objects.ParserException
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.stream.Collectors

abstract class BaseEngine(private val template: String): TemplateInf {
    protected lateinit var templateFile: String
    override fun generate(sws: ServerWebScript, userServerScript: String, params: Map<String, Any>): ServerWebScript {
        val inputStream = javaClass.getResourceAsStream("/scripttemplates/$template")
        if(inputStream != null)  {
            val reader = BufferedReader(InputStreamReader(inputStream))
            templateFile = reader.lines().collect(Collectors.joining(System.lineSeparator()))

            // TODO Here we can implement some security features
        } else {
            throw ParserException("Unable to retreive template $template")
        }

        return sws
    }
}