package doan.com.vn.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class GiaoVien extends Person{
    @Id
    @GenericGenerator(name = "gv-generator", 
      parameters = @Parameter(name = "prefix", value = "GV"), 
      strategy = "doan.com.vn.entity.GenerateIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gv-generator")
    @Column(name = "ma_gv")
    private String maGV;
    
    @Column(name = "anh_gv")
    private String anhGV;
    
    @Column(name = "hoc_van", columnDefinition = "NVARCHAR(200)")
    private String hocVan;
    
    
    
    @Column(name = "dien_thoai", length = 12)
    private String dienThoai;
    
    @Column(name = "ton_giao", columnDefinition = "NVARCHAR(200)")
    private String tonGiao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_dan_toc", referencedColumnName = "ma_dan_toc")
    private DanToc danToc;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_mon", referencedColumnName = "ma_mon")
    private MonHoc monHoc;
    
    @OneToMany(mappedBy = "giaoVien")
    private Set<Lop> lops;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_ban", referencedColumnName = "ma_ban")
    private Ban ban;
}
