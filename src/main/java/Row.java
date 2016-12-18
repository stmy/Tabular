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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {
    private List<Object> columns;
    
    public Row() {
        this(new Object[0]);
    }
    
    public Row(Object[] columns) {
        this.columns = new ArrayList<Object>(Arrays.asList(columns));
    }
    
    public Object[] all() {
        return columns.toArray();
    }
    
    public void add(Object value) {
        columns.add(value);
    }
    
    public void addAll(Object[] values) {
        columns.addAll(Arrays.asList(values));
    }
    
    public Object get(int index) {
        return columns.get(index);
    }
    
    public void set(int index, Object value) {
        columns.set(index, value);
    }
    
    public int size() {
        return columns.size();
    }
}
