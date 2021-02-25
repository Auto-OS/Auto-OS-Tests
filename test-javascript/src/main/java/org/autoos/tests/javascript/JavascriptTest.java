/*
 * MIT License
 *
 * Copyright (c) 2021 Luke Melaia.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package org.autoos.tests.javascript;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Tests running .js scripts within java, as well interfacing with the js code.
 *
 */
public class JavascriptTest {

    private static final Context JS_CONTEXT
            = Context.newBuilder("js").out(System.out).build();

    private static final Value JS_BINDING = JS_CONTEXT.getBindings("js");

    private JavascriptTest() {
        //Setup
        NativeCounter counter = new NativeCounter(1);
        JS_BINDING.putMember("native_counter", counter);
    }

    private void run() {
        //Start
        JS_CONTEXT.eval(parseInternalSourceScript("counter.js"));
        JS_CONTEXT.eval(parseInternalSourceScript("testScriptOne.js"));

        Value functionVariable = JS_BINDING.getMember("functionVariable");
        //functionVariable.execute();
    }

    public static void main(String[] args) {
        JavascriptTest test = new JavascriptTest();
        test.run();
    }

    public static Source parseInternalSourceScript(String name) {
        InputStream inputStream = JavascriptTest.class.getClassLoader().getResourceAsStream(name);

        if(inputStream == null)
            throw new IllegalArgumentException(new FileNotFoundException("Resource '" + name + "' not found in archive!"));

        StringBuilder scriptContent = new StringBuilder();
        int nextChar;

        try {
            while ((nextChar = inputStream.read()) != -1) {
                scriptContent.append((char)nextChar);
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }

        Source source = null;

        if(name.endsWith(".js"))
            source = Source.create("js", scriptContent.toString());

        return source;
    }
}
