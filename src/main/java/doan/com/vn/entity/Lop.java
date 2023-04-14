package doan.com.vn.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Lop extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ten_lop")
    private String tenLop;
    
    @Column(name = "nam_hoc")
    private String namHoc;
    
    @Column(name = "ten_khoi")
    private Integer tenKhoi;
    
    @Column(name = "si_so")
    private Integer siSo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lop")
    private Set<HocSinh> hocSinhs;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_gv", referencedColumnName = "ma_gv")
    private GiaoVien giaoVien;
}
