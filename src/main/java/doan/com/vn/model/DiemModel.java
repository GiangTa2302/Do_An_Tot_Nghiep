package doan.com.vn.model;

import lombok.Data;

@Data
public class DiemModel {
    private float diemTX1;
    private float diemMieng;
    private float diemTX2;
    private float diemThi;
    private int hocKy;
    private String maHS;
    private String maMon;
    private String hoTen;
    
    public DiemModel() {
        super();
    }
    
    public DiemModel(String maHS, String maMon, String hoTen) {
        super();
        this.maHS = maHS;
        this.maMon = maMon;
        this.hoTen = hoTen;
    }
    
    public float diemTB() {
        return ((diemMieng + diemTX1 + diemTX2*2)/4 + diemThi*2)/2;
    }
}
