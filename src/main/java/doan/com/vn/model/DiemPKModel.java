package doan.com.vn.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class DiemPKModel {
    @NotEmpty(message = "Mã học sinh không được để trống.")
    private String maHS;
    
    @NotEmpty(message = "Mã môn phải được chọn.")
    private String maMon;
    
    @Positive(message = "Học kỳ phải được chọn.")
    private Integer hocKy;
}
