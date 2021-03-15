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

package org.autoos.plugins;

import org.autoos.plugins.api.Plugin;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class PluginLoader {

    private final File pluginDirectory;

    public PluginLoader(File pluginDirectory) {
        this.pluginDirectory = pluginDirectory;
    }

    public Plugins load() {
        Plugins plugins = new Plugins();

        for(File pluginFile : Objects.requireNonNull(pluginDirectory.listFiles())) {
            if(pluginFile.getName().endsWith(".jar")){
                System.out.println("Found possible plugin: " + pluginFile.getName());

                try {
                    URLClassLoader child = new URLClassLoader (new URL[] {
                            pluginFile.toURI().toURL()
                    }, PluginLoader.class.getClassLoader());

                    System.out.println(Arrays.toString(child.getURLs()));

                    String content = "";
                    InputStream is = (InputStream)child.findResource("META-INF/MANIFEST.MF").getContent();
                    int i;
                    while(( i = is.read()) != -1) {
                        content += (char)i;
                    }

                    System.out.println(content);

                    Class classToLoad = Class.forName("org.autoos.tests.plugin1.TestPlugin1", true, child);
                    Method method = classToLoad.getDeclaredMethod("load");
                    Object instance = classToLoad.newInstance();
                    System.out.println("class: " + instance.getClass().getCanonicalName());

                    if(instance instanceof Plugin) {
                        System.out.println("INSTANCE OF PLUGIN");
                        Plugin<?> plugin = (Plugin<?>) instance;
                        System.out.println(plugin.toString());
                    }

                } catch (Exception e) {
                    System.err.println("Failed to load jar: " + pluginFile.getName());
                    System.err.println(e);
                }
            }
        }

        return plugins;
    }
}
