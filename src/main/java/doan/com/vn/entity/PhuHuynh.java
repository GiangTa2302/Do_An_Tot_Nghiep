package doan.com.vn.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class PhuHuynh extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "ph-generator", 
      parameters = @Parameter(name = "prefix", value = "PH"), 
      strategy = "doan.com.vn.entity.GenerateIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ph-generator")
    @Column(name = "ma_ph")
    private String maPH;
    
    @Column(name = "ten_ph", columnDefinition = "NVARCHAR(200)")
    private String tenPH;
    
    @Column(name = "nam_sinh")
    private Integer namSinh;
    
    @Column(name = "nghe_nghiep", columnDefinition = "NVARCHAR(200)")
    private String ngheNghiep;
    
    @Column(name = "loai_qh", columnDefinition = "NTEXT")
    private String loaiQH;
    
    @Column(name = "dien_thoai", length = 10)
    private String dienThoai;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "phuHuynh")
    private Set<HocSinh> hocSinhs;
}
