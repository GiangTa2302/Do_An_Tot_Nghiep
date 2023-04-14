package doan.com.vn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cong_van")
@Data
@EqualsAndHashCode(callSuper=false)
public class CongVan extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "cv-generator", 
      parameters = @Parameter(name = "prefix", value = "CV"), 
      strategy = "doan.com.vn.entity.GenerateIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cv-generator")
    @Column(name = "ma_cv")
    private String maCV;
    
    @Column(name = "tieu_de", columnDefinition = "NVARCHAR(500)")
    private String tieuDe;
    
    @Column(name = "noi_dung", columnDefinition = "TEXT")
    private String noiDung;
    
    @Column(name = "trang_thai")
    private boolean trangThai;
}
