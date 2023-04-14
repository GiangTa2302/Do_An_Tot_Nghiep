package doan.com.vn.model;

import java.util.Date;

import lombok.Data;

@Data
public class BaiVietModel {
    private Integer maBV;
    private String tieuDe;
    private String noiDung;
    private boolean trangThai;
    private String account;
    private Date createdDate;
}
