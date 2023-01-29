/*
 * Copyright(c) 2023 spaceup@iatlas.technology.
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

package technology.iatlas.sws.client.template

import technology.iatlas.sws.ServerWebScript
import technology.iatlas.sws.objects.ClientTemplate

class ClientTemplateGenerator {

    fun generate(): String {
        var template = ""
            getClientTemplateProps().forEach {
            val name = it.name
            val defaultValue = it.defaultValue
            val isMultiline = it.isMultiline
            val quotes = "\"\"\""

            if(isMultiline) {
                template += "$name: "
                template += "$quotes\n"
                template += defaultValue
                template += "\n$quotes"
            } else {
                template += "$name: $defaultValue"
            }
            template += "\n"
        }
        return template
    }

    private fun getClientTemplateProps(): List<ClientTemplate> {
        val clientTemplate = mutableListOf<ClientTemplate>()
        val iSWS = ServerWebScript::class

        iSWS.members.forEach {
            val annos = it.annotations
            if(annos.isNotEmpty()) {
                clientTemplate.add(annos.first { ct -> ct.annotationClass == ClientTemplate::class } as ClientTemplate)
            }
        }

        return clientTemplate
    }
}