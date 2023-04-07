package doan.com.vn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class TheBHYT extends Base {
    @Id
    @Column(name = "ma_the")
    private String maThe;
    
    @Column(name = "ngay_bd")
    private Date ngayBD;
    
    @Column(name = "ngay_kt")
    private Date ngayKT;
    
    @OneToOne(mappedBy = "theBHYT", fetch = FetchType.LAZY)
    private HocSinh hocSinh;
}
