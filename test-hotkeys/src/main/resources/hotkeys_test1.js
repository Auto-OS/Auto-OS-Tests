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

//$hotkeys.registerHotkey();

// $hotkeys.registerHotkey(['SHIFT', 'X'], function () {
//
// })
//
// registerHotkey({'':''}, function () {
//
// })

// if(false) {
//     console.log("A")
//     sleep(5000)
//     console.log("B")
//     sleep(2500)
//     console.log("C")
//     sleep(1000)
//     console.log("D")
//     sleep(500)
//     console.log("E")
//     sleep(100)
//     console.log("F")
//     sleep(10)
//     console.log("G")
// }

// doLater(5000, async function() {
//     console.log("Done later!")
// })

// $hotkeys.concurrently(async function() {
//     console.log("Done concurrently!")
// })

function q(val) {
    val({
        'cc': 22, 'ee': 44
    })
}

q((info) => {
    console.log(`Hi: ${info.ee}`)
})


console.log("hotkeys_test1.js")


c = new key_event(25, 6)
console.log(`key=${c.getKeyCode()},raw=${c.getRawCode()}`)


class _utils {
    static utilA() {
        return "A"
    }

    static utilB() {
        return "B"
    }
}


if($hotkeys === undefined) $hotkeys = {
    "textifyCallback": (callback) => {}, "requireUtils": () => {}
}


$hotkeys.requireUtils()
console.log(`Util: ${utils.utilB()}`)
optionalA.option()


$hotkeys.textifyCallback(
    callback = (arg1, arg2) => {
        console.log(`10 + 10 = ${10 + 10}`)

        console.log(arg1)
        console.log(arg2)
    }
)

//$hotkeys.exception()
//error()

$hotkeys.analyze({"a": "A", "b": "B", "1": 1, "-": [1,2,3,4,5]})

