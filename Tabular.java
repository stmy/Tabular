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
			sb.append(value.toString());
		}
		for (int i = 0; i < maxWidth - width; i++) {
			sb.append(" ");
		}
		if (isNumber) {
			sb.append(value.toString());
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
		return o.toString().length();
	}
}
