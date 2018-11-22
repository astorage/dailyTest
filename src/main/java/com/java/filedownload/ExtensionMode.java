package com.java.filedownload;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Author: Boris
 * Date: 2018/11/21 14:30
 * Copyright (C), 2017-2018, 浙江执御信息技术有限公司
 * Description:
 */
public enum ExtensionMode {
    TXT(".txt", "application/octet-stream") {
        @Override
        public void write(List<String[]> lines, String[] header, OutputStream outputStream) throws IOException {
            checkNotNull(lines, header, outputStream);
            final String headerString = Joiner.on(",").useForNull("").join(header);
            outputStream.write((headerString + "\r\n").getBytes(Charsets.UTF_8));
            for (String[] parts : lines) {
                final String line = Joiner.on(",").useForNull("").join(parts);
                outputStream.write((line + "\r\n").getBytes(Charsets.UTF_8));
            }
        }
    },
    CSV(".csv", "application/octet-stream") {
        @Override
        public void write(List<String[]> lines, String[] header, OutputStream outputStream) throws IOException {
            checkNotNull(lines, header, outputStream);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            final CSVWriter csvWriter = new CSVWriter(writer);

            try {
                csvWriter.writeNext(header,false);
                csvWriter.writeAll(lines, false);
                csvWriter.flush();
            } finally {
                csvWriter.close();
            }
        }
    },
    EXCEL2003(".xls", "application/vnd.ms-excel") {
        @Override
        public void write(List<String[]> lines, String[] header, OutputStream outputStream) throws IOException {
            checkNotNull(lines, header, outputStream);
            final Workbook workbook = new ExcelMaker() {
                @Override
                protected Workbook createWorkbook() {
                    return new HSSFWorkbook();
                }
            }.make(lines, header);
            workbook.write(outputStream);
        }
    },
    EXCEL2007(".xlsx", "application/vnd.ms-excel") {
        @Override
        public void write(List<String[]> lines, String[] header, OutputStream outputStream) throws IOException {
            checkNotNull(lines, header, outputStream);
            final Workbook workbook = new ExcelMaker() {
                @Override
                protected Workbook createWorkbook() {
                    return new XSSFWorkbook();
                }
            }.make(lines, header);
            workbook.write(outputStream);
        }
    };
    private static void checkNotNull(List<String[]> lines, String[] header, OutputStream outputStream) {
        Preconditions.checkNotNull(lines, "null list for write");
        Preconditions.checkNotNull(header, "null header");
        Preconditions.checkNotNull(outputStream, "null output stream ");
    }
    private final String suffix;
    private final String contentType;

    ExtensionMode(String suffix, String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getContentType() {
        return contentType;
    }

    public static ExtensionMode getExtensionModeBySuffix(String suffix) {
        for (ExtensionMode mode : ExtensionMode.values()) {
            if (mode.getSuffix().equalsIgnoreCase(suffix))
                return mode;
        }
        throw new IllegalArgumentException("Unknown File Type.");
    }

    public abstract void write(List<String[]> lines, String[] header, OutputStream outputStream)
            throws IOException;

    private abstract static class ExcelMaker {
        public Workbook make(List<String[]> lines, String[] header) {
            Workbook wb = createWorkbook();
            Sheet sheet = wb.createSheet("数据源");
            // 创建格式
            CellStyle cellStyle = getCellStyle(wb);
            // 添加第一行标题
            addHeader(header, sheet, cellStyle);
            // 从第二行开始写入数据
            addData(lines, sheet, cellStyle);
            return wb;
        }

        private void addData(List<String[]> lines, Sheet sheet, CellStyle cellStyle) {
            for (int sn = 0; sn < lines.size(); sn++) {
                String[] col = lines.get(sn);
                Row row = sheet.createRow(sn + 1);
                for (int cols = 0; cols < col.length; cols++) {
                    Cell cell = row.createCell(cols);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(col[cols]);
                }
            }
        }

        private CellStyle getCellStyle(Workbook wb) {
            Font font = wb.createFont();
            font.setColor(HSSFFont.COLOR_NORMAL);
            font.setBold(true);
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFont(font);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            return cellStyle;
        }

        private void addHeader(String[] header, Sheet sheet, CellStyle cellStyle) {
            Row titleRow = sheet.createRow(0);
            // 创建第1行标题单元格
            for (int i = 0, size = header.length; i < size; i++) {
                switch (i) {
                    case 5:
                    case 7:
                        sheet.setColumnWidth(i, 10000);
                        break;
                    default:
                        sheet.setColumnWidth(i, 3500);
                        break;
                }
                Cell cell = titleRow.createCell(i, CellType.STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(header[i]);
            }
        }
        protected abstract Workbook createWorkbook();
    }


}
