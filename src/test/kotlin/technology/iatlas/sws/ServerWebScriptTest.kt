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

package technology.iatlas.sws

import org.apache.logging.log4j.kotlin.Logging
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class ServerWebScriptTest: Logging {
    private val swsBaseFile = ServerWebScript::class.java.getResource("/testdata/basic.sws").file

    @Test
    @Order(1)
    fun createServerWebScriptObject() {
        logger.info("Create only the object")
        val sws: ServerWebScript = SWSCreator.create(File(swsBaseFile))
        assertNotNull(sws, "ServerWebScript is not null")
    }

    @Test
    @Order(2)
    fun createAndParseHoster() {
        logger.info("Create and parse the object")
        val sws: ServerWebScript = SWSCreator.createAndParse(File(swsBaseFile))
        assertNotNull(sws, "ServerWebScript is not null")
    }

    @Test
    @Order(3)
    fun testHoster() {
        // 'create' works as it is default, else it would fail
        val sws = SWSCreator.create(File(swsBaseFile))
        assertEquals("Uberspace", sws.hoster)
    }

    @Test
    @Order(4)
    fun testEndpoint() {
        val sws: ServerWebScript = SWSCreator.createAndParse(File(swsBaseFile))
        assertEquals("GET", sws.serverEndpoint.httpAction)
        assertEquals("/test/basic", sws.serverEndpoint.url)
    }

    @Test
    @Order(5)
    fun testServerLang() {
        val sws: ServerWebScript = SWSCreator.createAndParse(File(swsBaseFile))
        assertEquals("/usr/bin/env bash", sws.serverLang)
    }
}