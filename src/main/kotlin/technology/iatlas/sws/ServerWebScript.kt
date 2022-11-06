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

package technology.iatlas.sws

import technology.iatlas.sws.objects.Endpoint
import technology.iatlas.sws.ruleengine.rules.Rule
import java.io.File

sealed interface ServerWebScript {

    /**
     * Defines the name of the server web scripts
     *
     * Property: NAME
     */
    var name: String

    /**
     * Defines where we should run.
     *
     * Property: HOSTER
     *
     * Default: Uberspace
     */
    var hoster: String

    /**
     * Hold the swagger documentation
     *
     * Property: SWAGGER_DOC
     */
    var swaggerDoc: String
    /**
     * Defines the script we should execute
     *
     * Property: SERVER_LANG
     */
    var serverLang: String
    /**
     * Defines the response object language
     *
     * Property: CLIENT_LANG
     */
    var clientLang: String
    /**
     * Defines the endpoint where we should be exposed to
     *
     * Property: SERVER_ENDPOINT
     */
    var serverEndpoint: Endpoint
    /**
     * Depending on the server language, we define the script which will run on the server
     *
     * Property: SERVER_SCRIPT
     */
    var serverScript: String
    /**
     * Represents the return values from the script
     *
     * Property: SERVER_RESPONSE_OBJECTS
     *
     * Example: SERVER_RESPONSE_OBJECTS: obj1; Obj2; ...
     */
    var serverResponseObjects: List<Any>
    /**
     * Present the response object for the endpoint
     *
     * Property: CLIENT_RESPONSE
     */
    //var clientResponse: String

    /**
     * Set/Get dynamic url parameters
     */
    var urlParams: MutableMap<String, Any?>

    /**
     * Present the http body
     */
    var httpBody: MutableMap<String, Any?>

    fun parse(file: File): SWS

    fun parse(file: File, rules: List<Rule>): SWS

    //abstract fun parse(content: String)
}