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

import org.apache.logging.log4j.kotlin.Logging
import technology.iatlas.sws.ServerWebScript
import java.io.File

abstract class BaseRule(private val rule: String): Rule, Logging {
    protected val baseFile: File = File.createTempFile("base-",".sws")
    /**
     * It's important to call super.proceed(file) to do the base parsing
     */
    override fun process(sws: ServerWebScript, rawFile: File) {
        logger.info("Process rule $rule")
        logger.debug("Clear SWS file.")

        rawFile.forEachLine {
            // Ignore comments and blank lines
            if(!it.contains("#") && it.isNotBlank()) {
                baseFile.appendText("$it\n")
            }
        }
    }
}