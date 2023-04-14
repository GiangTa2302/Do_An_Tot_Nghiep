package doan.com.vn.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Cau_Hoi")
@Data
@EqualsAndHashCode(callSuper=false)
public class CauHoi extends Base{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_ch")
    private Integer maCH;
    
    @Column(name = "de_bai", columnDefinition = "NVARCHAR(2000)")
    private String deBai;
    
    private String anh;
    
    private String dapAnDung;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_de", referencedColumnName = "ma_de")
    private De de;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cauHoi")
    private List<DapAn> dapAns;
}
