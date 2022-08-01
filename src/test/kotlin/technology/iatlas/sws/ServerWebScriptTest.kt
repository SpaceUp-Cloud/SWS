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

package technology.iatlas.sws

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import technology.iatlas.sws.ruleengine.rules.*
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class ServerWebScriptTest {
    private val logger = KotlinLogging.logger {}
    private val swsBaseFile = ServerWebScript::class.java.getResource("/testdata/basic.sws").file

    @Test
    fun createAndParseWebScriptObject() {
        logger.info("Create and parse the object")
        val sws: ServerWebScript = SWS.createAndParse(File(swsBaseFile))
        assertNotNull(sws, "ServerWebScript is not null")
    }

    @Test
    fun testName() {
        val sws = SWS.createAndParse(File(swsBaseFile), listOf(NameRule()))
        assertEquals("Basic SWS", sws.name)
    }

    @Test
    fun testHoster() {
        // 'create' works as it is default, else it would fail
        val sws = SWS.createAndParse(File(swsBaseFile), listOf(HosterRule()))
        assertEquals("Uberspace", sws.hoster)
    }

    @Test
    fun testEndpoint() {
        val sws: ServerWebScript =
            SWS.createAndParse(File(swsBaseFile), listOf(EndpointRule(mutableMapOf())))
        assertEquals("GET", sws.serverEndpoint.httpAction)
        assertEquals("/test/basic?param1=defaultValue&param2=&intparam=", sws.serverEndpoint.url)
    }

    @Test
    fun testServerLang() {
        val sws: ServerWebScript = SWS.createAndParse(File(swsBaseFile), listOf(LangRule()))
        assertEquals("#!/usr/bin/env bash", sws.serverLang)
    }

    @Test
    fun testSwaggerDoc() {
        val docString = "Just to lazy to write something useful here."
        val sws: ServerWebScript =
            SWS.createAndParse(File(swsBaseFile), listOf(SwaggerRule()))
        assertEquals(docString, sws.swaggerDoc)
    }

    @Test
    fun testEndpointParameters() {
        // Simulate HTTP request parameters
        val urlParams = mutableMapOf<String, Any?>()
        urlParams["param1"] = null
        urlParams["param2"] = "test2"
        urlParams["intparam"] = 42

        val sws = SWS.createAndParse(File(swsBaseFile),
            listOf(EndpointRule(urlParams)))

        val params = sws.serverEndpoint.getUrlParams()

        assertEquals(true, params.containsKey("param1"))
        assertEquals("defaultValue", params["param1"])
        assertEquals("test2", params["param2"])
        assertEquals(42, params["intparam"])
        assertEquals(3, sws.serverEndpoint.getUrlParams().size)
    }

    @Test
    fun testServerScriptRule() {
        val urlParams = mutableMapOf<String, Any?>()
        urlParams["param1"] = null
        urlParams["param2"] = "myValue"

        val sws = SWS.createAndParse(File(swsBaseFile), urlParams)

        val expected = "#!/usr/bin/env bash"
        assertNotNull(sws.serverScript)
        assertNotEquals("", sws.serverScript)
        // From the bash template
        assertEquals(expected, sws.serverLang)
        assertTrue(sws.serverScript.contains(expected))
    }

    @Test
    fun testAllProps() {
        val sws: ServerWebScript = SWS.createAndParse(File(swsBaseFile))
        assertNotNull(sws.name)
        assertNotNull(sws.hoster)
        assertNotNull(sws.serverEndpoint)
        assertNotNull(sws.serverScript)
        assertNotNull(sws.swaggerDoc)
    }
}