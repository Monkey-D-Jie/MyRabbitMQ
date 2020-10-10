package com.jf.jf_isomc1000.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * @author Hu Xinyao 2019/8/16
 * @description：
 */
public class PoiUtil {

    /**
     * 创建结果集对应的单元格
     *
     * @param column 列
     * @param row    行
     * @param style  风格
     * @param value  值
     */
    public static void createExcelCell(int column, XSSFRow row, XSSFCellStyle style, String value) {

        XSSFCell cell = row.createCell(column, XSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }
}
