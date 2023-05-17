package doan.com.vn.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import doan.com.vn.model.GiaoVienModel;

public class ImportExcel {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "STT", "Họ đệm", "Tên", "Ngày sinh",
            "Giới tính", "Điện thoại", "Địa chỉ", "Tôn giáo", "Dân tộc",
            "Học vấn" };
    static String SHEET = "Sheet1";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<GiaoVienModel> excelToTeacher(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<GiaoVienModel> giaoVienModels = new ArrayList<GiaoVienModel>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                GiaoVienModel giaoVien = new GiaoVienModel();

                int cellIdx = 1;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                    case 1:
                        giaoVien.setHodem(currentCell.getStringCellValue());
                        break;

                    case 2:
                        giaoVien.setTen(currentCell.getStringCellValue());
                        break;

                    case 3:
                        giaoVien.setNgaySinh(currentCell.getDateCellValue());
                        break;

                    case 4:
                        giaoVien.setGioiTinh(currentCell.getBooleanCellValue());
                        break;

                    case 5:
                        giaoVien.setDienThoai(currentCell.getStringCellValue());
                        break;
                    case 6: {
                        String[] diaChi = currentCell.getStringCellValue()
                                .split("-");
                        if (diaChi.length > 2) {
                            giaoVien.setPhuong(diaChi[0]);
                            giaoVien.setQuan(diaChi[1]);
                            giaoVien.setTinh(diaChi[2]);
                        }
                        break;
                    }
                    case 7:
                        giaoVien.setTonGiao(currentCell.getStringCellValue());
                        break;
                    case 8:
                        giaoVien.setMaDanToc(Integer
                                .parseInt(currentCell.getStringCellValue()));
                        break;
                    case 9:
                        giaoVien.setHocVan(currentCell.getStringCellValue());
                        break;

                    default:
                        break;
                    }

                    cellIdx++;
                }

                giaoVienModels.add(giaoVien);
            }

            workbook.close();

            return giaoVienModels;
        } catch (IOException e) {
            throw new RuntimeException(
                    "fail to parse Excel file: " + e.getMessage());
        }
    }
}
