package doan.com.vn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Bai_Viet")
@Data
@EqualsAndHashCode(callSuper=false)
public class BaiViet extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_bv")
    private Integer maBV;
    
    @Column(name = "tieu_de", columnDefinition = "NVARCHAR(1000)")
    private String tieuDe;
    
    @Column(name = "noi_dung", columnDefinition = "NTEXT")
    private String noiDung;
    
    @Column(name = "trang_thai")
    private boolean trangThai;
    
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
}
