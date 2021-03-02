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

import org.graalvm.polyglot.Source;

import java.io.*;
import java.util.Objects;

public class JSSource {

    private final String name;

    private final String sourceCodeContent;

    protected JSSource(String sourceCodeContent, String name) {
        this.sourceCodeContent = Objects.requireNonNull(sourceCodeContent);
        this.name = Objects.requireNonNull(name);
    }

    public Source getSource() throws IOException {
        return Source.newBuilder("js", sourceCodeContent, name).build();
    }

    public String toString() {
        return this.sourceCodeContent;
    }

    public static JSSource getSourceFromString(String sourceCode) {
        return new JSSource(sourceCode, "no-name.js");
    }

    public static JSSource getSourceFromArchivedFile(String archivePath) throws IOException {
        InputStream inputStream = JSSource.class.getClassLoader().getResourceAsStream(archivePath);

        if(inputStream == null)
            throw new FileNotFoundException("Resource file '" + archivePath + "' not found in archive!");

        StringBuilder scriptContent = new StringBuilder();
        int nextChar;
        while ((nextChar = inputStream.read()) != -1) {
            scriptContent.append((char)nextChar);
        }

        return new JSSource(scriptContent.toString(), archivePath);
    }

    public static JSSource getSourceFromFile(File sourceFile) throws IOException {
        if(!sourceFile.exists())
            throw new FileNotFoundException("The source file '" + sourceFile.getPath() + "' does not exist.");

        if(!sourceFile.getPath().endsWith(".js"))
            System.out.println("The source file '" + sourceFile.getPath() + "' does not have a Javascript extension.");

        FileInputStream fis = new FileInputStream(sourceFile);
        StringBuilder fileSource = new StringBuilder();

        int nextChar;
        while((nextChar = fis.read()) != -1)
            fileSource.append((char) nextChar);
        fis.close();

        return new JSSource(fileSource.toString(), sourceFile.getName());
    }
}
