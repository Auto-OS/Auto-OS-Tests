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

import org.autoos.tests.hotkeys.script.JSPlugin;
import org.autoos.tests.hotkeys.script.JSProvider;
import org.autoos.tests.hotkeys.script.JSSource;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class HotkeysPlugin extends JSPlugin {

    public HotkeysPlugin() {
        super("hotkeys");
    }

    @Override
    protected void initialize() {
        try {
            LogManager.getLogManager().reset();
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            org.jnativehook.GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
            GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
            GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
        } catch (NativeHookException e) {
            System.err.println("Failed to register global native hook.");
            e.printStackTrace();
        }
    }

    @Override
    protected JSProvider[] getProviders() {
        return new JSProvider[]{
                new HotkeysProvider()
        };
    }

    @Override
    protected JSSource[] getLibrarySources() throws Exception {
        return new JSSource[] {
                JSSource.getSourceFromArchivedFile("libraries/hotkeys.js")
        };
    }

    @Override
    protected JSSource[] getStandardLibrarySources() throws Exception {
        return new JSSource[] {
                JSSource.getSourceFromArchivedFile("libraries/key_event.js")
        };
    }

}
