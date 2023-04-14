package doan.com.vn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class De extends Base{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "de-generator", 
      parameters = @Parameter(name = "prefix", value = "DE"), 
      strategy = "doan.com.vn.entity.GenerateIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "de-generator")
    @Column(name = "ma_de")
    private String maDe;
    
    private Integer thoiGian;
    
    private Integer soCau;
    
    private boolean loaiDe;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_mon", referencedColumnName = "ma_mon")
    private MonHoc monHoc;
}
