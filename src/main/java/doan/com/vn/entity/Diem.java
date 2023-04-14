package doan.com.vn.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Diem extends Base{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DiemPK id;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("maHS")
    @JoinColumn(name = "ma_hs")
    private HocSinh hocSinh;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("maMon")
    @JoinColumn(name = "ma_mon")
    private MonHoc monHoc;
    
    @Column(name = "diem_tx_1")
    private float diemTX1;
    
    @Column(name = "diem_mieng")
    private float diemMieng;
    
    @Column(name = "diem_tx_2")
    private float diemTX2;
    
    @Column(name = "diem_thi")
    private float diemThi;
    
    
    public float diemTB() {
        return ((diemMieng + diemTX1 + diemTX2*2)/4 + diemThi*2)/2;
    }
}
