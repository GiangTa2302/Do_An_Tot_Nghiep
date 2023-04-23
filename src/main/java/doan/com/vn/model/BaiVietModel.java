package doan.com.vn.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class BaiVietModel {
    private Integer maBV;
    
    @NotEmpty(message = "Tiêu đề không được để trống")
    private String tieuDe;
    
    @NotEmpty(message = "Nội dung không được để trống")
    private String noiDung;
    
    private boolean trangThai;
    private String username;
    private Date createdDate;
}
