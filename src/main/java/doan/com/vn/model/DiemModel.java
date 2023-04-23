package doan.com.vn.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class DiemModel {
    private float diemTX1;
    
    private float diemMieng;
    
    private float diemTX2;
    
    private float diemThi;
    
    @Positive(message = "Học kỳ phải được chọn.")
    private Integer hocKy;
    
    @NotEmpty(message = "Mã học sinh không được để trống.")
    private String maHS;
    
    private String maMon;
    
    private String hoTen;
    
    public DiemModel() {
        super();
    }
    
    public DiemModel(String maHS, String maMon, String hoTen, Integer hocKy) {
        super();
        this.maHS = maHS;
        this.maMon = maMon;
        this.hoTen = hoTen;
        this.hocKy = hocKy;
    }
    
    public float diemTB() {
        return ((diemMieng + diemTX1 + diemTX2*2)/4 + diemThi*2)/3;
    }
    
    public Integer xepLoaiHL() {
        if(diemTB() >= 8.0) {
            return 1;
        } else if(diemTB() >= 6.5) {
            return 2;
        } else if(diemTB() >= 5.0) {
            return 3;
        } else if(diemTB() >= 3.5) {
            return 4;
        } else {
            return 5;
        }
    }
}
