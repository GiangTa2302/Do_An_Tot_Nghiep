package doan.com.vn.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class DanToc extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_dan_toc")
    private Integer maDanToc;
    
    @Column(name = "ten_dan_toc", columnDefinition = "NVARCHAR(100)")
    private String tenDanToc;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "danToc")
    private Set<HocSinh> hocSinhs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "danToc")
    private Set<GiaoVien> giaoViens;
}
