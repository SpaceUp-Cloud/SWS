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

abstract class SWSBase: ServerWebScript {
    /**
     * Presents comments in the script
     */
    lateinit var _metaComments: List<String>
    /**
     * Defines where we should run.
     *
     * Property: HOSTER
     *
     * Default: Uberspace
     */
    var hoster: String = "Uberspace"
    /**
     * Hold the swagger documentation
     *
     * Property: SWAGGER_DOC
     */
    lateinit var swaggerDoc: String
    /**
     * Defines the script we should execute
     * 
     * Property: SERVER_LANG
     */
    lateinit var serverLang: String
    /**
     * Defines the response object language
     * 
     * Property: CLIENT_LANG
     */
    lateinit var clientLang: String
    /**
     * Defines the endpoint where we should be exposed to
     *
     * Property: SERVER_ENDPOINT
     */
    lateinit var serverEndpoint: Endpoint
    /**
     * Depending on the server language, we define the script which will run on the server
     *
     * Property: SERVER_SCRIPT
     */
    lateinit var serverScript: String
    /**
     * Represents the return values from the script
     *
     * Property: SERVER_RESPONSE_OBJECTS
     *
     * Example: SERVER_RESPONSE_OBJECTS: obj1; Obj2; ...
     */
    lateinit var serverResponseObjects: List<Any>
    /**
     * Present the response object for the endpoint
     *
     * Property: CLIENT_RESPONSE
     */
    lateinit var clientResponse: String
}