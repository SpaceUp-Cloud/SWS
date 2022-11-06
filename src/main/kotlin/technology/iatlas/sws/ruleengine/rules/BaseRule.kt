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

import mu.KotlinLogging
import technology.iatlas.sws.SWS
import java.io.File

abstract class BaseRule(protected val rule: String): Rule {
    protected val logger = KotlinLogging.logger {}

    /**
     * It's important to call super.proceed(file) to do the base parsing.
     * After doing the pre-parse stuff, you can use 'baseFile' for further processing.
     */
    override fun process(sws: SWS, swsFile: File, parse: (sws: File) -> SWS): SWS {
        logger.info("Process rule $rule")
        return processRule(swsFile, parse)
    }

    private fun processRule(swsFile: File, parse: (sws: File) -> SWS): SWS {
        // Clear all comments
        logger.debug("Clear comments")
        val commentRegex = Regex("(?!#!)(#.*)", RegexOption.UNIX_LINES)
        var processedContent = swsFile.readText().replace(commentRegex, "").trim()
        processedContent = processedContent.replace(Regex("(?!\\w*)( *)"), "")

        var cleanedFile = ""
        processedContent.lines().forEach {
            if(it.isNotEmpty() || it.isNotBlank() || !it.contains("")) {
                cleanedFile += it + "\n"
            }
        }

        // Done - write result
        val processedFile = File(rule)
        processedFile.writeText(cleanedFile)
        val sws = parse(processedFile)
        processedFile.delete()

        return sws
    }

}