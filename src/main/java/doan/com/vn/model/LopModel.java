package doan.com.vn.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LopModel {
    @NotEmpty(message = "Tên lớp không được để trống.")
    private String tenLop;
    
    @NotEmpty(message = "Năm học không được để trống.")
    private String namHoc;
    
    private Integer tenKhoi;
    
    private Integer siSo;
    
    @NotEmpty(message = "Giáo viên không được để trống.")
    private String maGV;
}
