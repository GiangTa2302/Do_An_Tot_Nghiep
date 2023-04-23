package doan.com.vn.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;

@Entity
@Data
public class GiaoVien extends Person {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "gv-generator", parameters = @Parameter(name = "prefix", value = "GV"), strategy = "doan.com.vn.entity.GenerateIdSequence")
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "GV_Lop", joinColumns = @JoinColumn(name = "ma_gv"), inverseJoinColumns = @JoinColumn(name = "ten_lop"))
    private List<Lop> lops;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        GiaoVien other = (GiaoVien) obj;
        return Objects.equals(maGV, other.maGV);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(maGV);
        return result;
    }

}
