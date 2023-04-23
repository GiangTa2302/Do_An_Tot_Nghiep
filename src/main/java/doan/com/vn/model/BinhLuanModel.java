package doan.com.vn.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class BinhLuanModel {
    @NotEmpty(message = "Nội dung không được để trống.")
    private String noiDung;
    private Date createdDate;
    private String username;
}
