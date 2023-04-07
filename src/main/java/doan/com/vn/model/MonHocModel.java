package doan.com.vn.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class MonHocModel {
    @NotEmpty(message = "Tên môn không được để trống")
    private String tenMon;
    
    @Positive(message = "Số tiết lý thuyết > 0")
    private int soTietLT;
    
    @PositiveOrZero(message = "Số tiết thực hành >= 0")
    private int soTietTH;
}
