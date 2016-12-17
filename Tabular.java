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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Tabular {
	protected Optional<Row> header;
	protected List<Row> rows;
	
	protected String separator = " ";
	
	public Tabular() {
		header = Optional.empty();
		rows = new ArrayList<Row>();
	}
	
	public void addRow(Row row) {
		rows.add(row);
	}
	
	public void addRow(Object[] columns) {
		rows.add(new Row(columns));
	}
	
	public void setRow(int index, Row row) {
		rows.set(index, row);
	}
	
	public Row getRow(int index) {
		return rows.get(index);
	}
	
	public void setHeader(Row header) {
		this.header = Optional.of(header);
	}
	
	public void setHeader(Object[] columns) {
		this.setHeader(new Row(columns));
	}
	
	public Row getHeader() {
		return header.orElse(null);
	}
	
	public void clear() {
		header = Optional.empty();
		rows.clear();
	}
	
	@Override
	public String toString() {
		int[] maxWidths = getMaximumWidths();

		boolean isFirst = true;
		
		StringBuilder sb = new StringBuilder();
		if (header.isPresent()) {
			printRow(sb, maxWidths, header.get());
			isFirst = false;
		}
		
		for (Row row : rows) {
			if (!isFirst) {
				sb.append("\n");
			}
			printRow(sb, maxWidths, row);
			isFirst = false;
		}
		
		return sb.toString();
	}
	
	protected void printRow(StringBuilder sb, int[] maxWidths, Row row) {
		int columns = row.size();
		for (int i = 0; i < maxWidths.length; i++) {
			if (i < columns) {
				printValue(sb, maxWidths[i], row.get(i));
			} else {
				printValue(sb, maxWidths[i], "");
			}
			
			if (i < maxWidths.length - 1) {
				sb.append(separator);
			}
		}
	}
	
	protected void printValue(StringBuilder sb, int maxWidth, Object value) {
		int width = getStringWidth(value.toString());
		boolean isNumber = value instanceof Number;
		
		if (!isNumber) {
			sb.append(value != null ? value.toString() : "");
		}
		for (int i = 0; i < maxWidth - width; i++) {
			sb.append(" ");
		}
		if (isNumber) {
			sb.append(value != null ? value.toString() : "");
		}
	}
	
	protected int getColumnSize() {
		int headerColumns = header.isPresent()
								? header.get().size() : 0;  
		int maxBodyColumns = rows.stream()
								.map(x -> x.size())
								.max(Comparator.comparing(x -> x))
								.get();
		
		return Math.max(headerColumns, maxBodyColumns);
	}
	
	protected int[] getMaximumWidths() {
		int[] widths = new int[getColumnSize()];
		
		for (int index = 0; index < widths.length; index++) {
			widths[index] = getMaximumWidth(index);
		}
		
		return widths;
	}
	
	protected int getMaximumWidth(int index) {
		int headerWidth = header.isPresent()
							? getStringWidth(header.get().get(index)) : 0;
		int maxBodyWidth = rows.stream()
							.map(x -> (x.size() <= index) ? 0 : getStringWidth(x.get(index))) // 行に存在しない列は0幅扱い
							.max(Comparator.comparing(x -> x))
							.get();
		
		return Math.max(headerWidth, maxBodyWidth);
	}
	
	protected int getStringWidth(Object o) {
		return o != null ? o.toString().length() : 0;
	}
}
