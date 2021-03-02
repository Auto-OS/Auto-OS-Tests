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

package org.autoos.tests.hotkeys.script;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.IOException;

public class JSEngine {

    public final Context context;

    public final Value bindings;

    public JSEngine(JSPlugin... plugins) {
        this.context = Context.newBuilder("js").out(System.out).build();
        this.bindings = context.getBindings("js");

        for(JSPlugin plugin : plugins) {
            plugin.initialize();

            try {
                for (JSSource source : plugin.getStandardLibrarySources()) {
                    context.eval(source.getSource());
                }
            } catch (Exception e) {
                System.err.println("Failed to load the standard javascript libraries for the plugin: '" + plugin.getPluginName());
            }

            for(JSProvider provider : plugin.getProviders())
                this.bindings.putMember(provider.getName(), provider);

            try {
                for (JSSource source : plugin.getLibrarySources()) {
                    context.eval(source.getSource());
                }
            } catch (Exception e) {
                System.err.println("Failed to load the javascript libraries for the plugin: '" + plugin.getPluginName());
            }
        }
    }

    public JSScript parseSource(JSSource source) throws IOException {
        return new JSScript(context.parse(source.getSource()), bindings);
    }
}
