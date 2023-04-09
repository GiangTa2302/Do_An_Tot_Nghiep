package doan.com.vn.model;

import java.util.Date;

import lombok.Data;

@Data
public class BaiGiangModel {
    private Integer maBG;
    private String anhBia;
    private String tieuDe;
    private String moTa;
    private long soBL;
    private Date createdDate;
}
