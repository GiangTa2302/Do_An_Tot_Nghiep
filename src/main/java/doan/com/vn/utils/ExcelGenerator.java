package doan.com.vn.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import doan.com.vn.entity.Diem;

public class ExcelGenerator {
    private List<Diem> diems;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List<Diem> diems) {
        this.diems = diems;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Diem");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "STT", style);
        createCell(row, 1, "Họ và tên", style);
        createCell(row, 2, "Điểm miệng", style);
        createCell(row, 3, "Điểm 15'", style);
        createCell(row, 4, "Điểm giữa kỳ", style);
        createCell(row, 5, "Điểm cuối kỳ'", style);
        createCell(row, 6, "Điểm TB'", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell,
            CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Float) {
            cell.setCellValue(String.valueOf(valueOfCell));
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Diem record : diems) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, columnCount++, style);
            createCell(row, columnCount++, record.getHocSinh().getHodem() + " "
                    + record.getHocSinh().getTen(), style);
            createCell(row, columnCount++, record.getDiemMieng(), style);
            createCell(row, columnCount++, record.getDiemTX1(), style);
            createCell(row, columnCount++, record.getDiemTX2(), style);
            createCell(row, columnCount++, record.getDiemThi(), style);
            createCell(row, columnCount++, record.diemTB(), style);
        }
    }

    public void generateExcelFile(HttpServletResponse response)
            throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
