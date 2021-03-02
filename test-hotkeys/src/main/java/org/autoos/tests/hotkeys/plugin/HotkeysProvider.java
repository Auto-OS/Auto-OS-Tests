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

package org.autoos.tests.hotkeys.plugin;

import org.autoos.tests.hotkeys.HotkeysTest;
import org.autoos.tests.hotkeys.script.JSProvider;
import org.autoos.tests.hotkeys.script.JSSource;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.io.IOException;

public class HotkeysProvider implements JSProvider {

    @HostAccess.Export()
    public void registerHotkey(Value keys, Value callback) {
        if(!callback.canExecute())
            throw new IllegalArgumentException("Must provide a callback!");

        if(!keys.hasArrayElements())
            throw new IllegalArgumentException("No keys");

        for(int i = 0; i < keys.getArraySize(); i++) {
            System.out.println("Element '" + i + "': " + keys.getArrayElement(i));
        }
    }

    @HostAccess.Export
    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Cannot sleep");
        }
    }

    /**
     * @throws IllegalStateException always throws an {@link IllegalStateException}
     * due to concurrent execution from multiple threads.
     */
    @HostAccess.Export
    public void doLater(long time, Value callback) {
        sleep(time);
        Thread doLater = new Thread("Do-Later-Thread") {
            @Override
            public void run() {
                callback.execute();
            }
        };
        doLater.setDaemon(true);
        doLater.start();
    }

    @HostAccess.Export
    public void requireUtils() throws IOException {
        HotkeysTest.engine.parseSource(JSSource.getSourceFromArchivedFile("libraries/optional.js")).execute();
        Value utilsClass = HotkeysTest.engine.bindings.getMember("_utils");
        HotkeysTest.engine.bindings.putMember("utils", utilsClass);
    }

    @Override
    public String getName() {
        return "$hotkeys";
    }
}
