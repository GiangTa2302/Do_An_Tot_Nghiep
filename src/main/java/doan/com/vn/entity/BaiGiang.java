package doan.com.vn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Bai_Giang")
@Data
@EqualsAndHashCode(callSuper=false)
public class BaiGiang extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_bg")
    private Integer maBG;
    
    @Column(name = "anh_bia")
    private String anhBia;
    
    @Column(name = "tieu_de", columnDefinition = "NVARCHAR(1000)")
    private String tieuDe;
    
    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;
}
