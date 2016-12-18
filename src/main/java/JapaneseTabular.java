/*
 * Copyright 2016- stmy <http://stmy.github.io/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * <http://www.apache.org/licenses/LICENSE-2.0>
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.stmy.util.tabular;

public class JapaneseTabular extends Tabular {
    @Override
    protected int getStringWidth(Object o) {
        int width = 0;
        for (int c : o.toString().toCharArray()) {
            // 本来ならUnicodeのEast Asian Widthプロパティを参照するべきだが、
            // http://www.alqmst.co.jp/tech/040601.html の方法で判定
            if (c <  0x007e || // a-zA-Z0-9
                c == 0x00a5 || // \
                c == 0x203e || // ~
                (c >= 0xff61 && c <= 0xff9f) // 半角カナ
                ) {
                width += 1;
            } else {
                width += 2;
            }
        }
        return width;
    }
}
