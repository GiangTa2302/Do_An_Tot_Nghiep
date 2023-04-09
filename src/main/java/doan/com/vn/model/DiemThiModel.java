package doan.com.vn.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiemThiModel {
    @NotEmpty(message = "Mã học sinh không được để trống!")
    private String maHS;
    
    private Integer hocKy;
}
