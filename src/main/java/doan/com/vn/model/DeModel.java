package doan.com.vn.model;

import java.util.Date;

import lombok.Data;

@Data
public class DeModel {
    private Integer thoiGian;
    private Integer soCau;
    private String maMon;
    private Date createdDate;
    private boolean loaiDe;

    public DeModel(boolean loaiDe) {
        super();
        this.loaiDe = loaiDe;
    }

    public DeModel() {
        super();
    }

}
