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
import org.junit.jupiter.api.Test
import technology.iatlas.sws.ruleengine.rules.EndpointRule
import technology.iatlas.sws.ruleengine.rules.HosterRule
import technology.iatlas.sws.ruleengine.rules.LangRule
import technology.iatlas.sws.ruleengine.rules.SwaggerRule
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class ServerWebScriptTest: Logging {
    private val swsBaseFile = ServerWebScript::class.java.getResource("/testdata/basic.sws").file

    @Test
    fun createServerWebScriptObject() {
        logger.info("Create only the object")
        val sws: ServerWebScript = SWSCreator.create(File(swsBaseFile))
        assertNotNull(sws, "ServerWebScript is not null")
    }

    @Test
    fun createAndParseWebScriptObject() {
        logger.info("Create and parse the object")
        val sws: ServerWebScript = SWSCreator.createAndParse(File(swsBaseFile))
        assertNotNull(sws, "ServerWebScript is not null")
    }

    @Test
    fun testHoster() {
        // 'create' works as it is default, else it would fail
        val sws = SWSCreator.createAndParse(File(swsBaseFile), listOf(HosterRule()))
        assertEquals("Uberspace", sws.hoster)
    }

    @Test
    fun testEndpoint() {
        val sws: ServerWebScript =
            SWSCreator.createAndParse(File(swsBaseFile), listOf(EndpointRule()))
        assertEquals("GET", sws.serverEndpoint.httpAction)
        assertEquals("/test/basic", sws.serverEndpoint.url)
    }

    @Test
    fun testServerLang() {
        val sws: ServerWebScript = SWSCreator.createAndParse(File(swsBaseFile), listOf(LangRule()))
        assertEquals("/usr/bin/env bash", sws.serverLang)
    }

    @Test
    fun testSwaggerDoc() {
        val docString = "Just to lazy to write something useful here."
        val sws: ServerWebScript =
            SWSCreator.createAndParse(File(swsBaseFile), listOf(SwaggerRule()))
        assertEquals(docString, sws.swaggerDoc)
    }

    @Test
    fun testAllProps() {
        val sws: ServerWebScript = SWSCreator.createAndParse(File(swsBaseFile))
        logger.debug(sws.toString())
        assertNotNull(sws.hoster)
        assertNotNull(sws.serverEndpoint)
        assertNotNull(sws.serverScript)
        assertNotNull(sws.swaggerDoc)
    }
}