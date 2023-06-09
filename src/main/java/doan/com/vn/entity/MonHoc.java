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
public class MonHoc extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "mh-generator", 
      parameters = @Parameter(name = "prefix", value = "MH"), 
      strategy = "doan.com.vn.entity.GenerateIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mh-generator")
    @Column(name = "ma_mon")
    private String maMon;
    
    @Column(name = "ten_mon", columnDefinition = "NVARCHAR(200)")
    private String tenMon;
    
    @Column(name = "so_tiet_lt")
    private Integer soTietLT;
    
    @Column(name = "so_tiet_th")
    private Integer soTietTH;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_ban", referencedColumnName = "ma_ban")
    private Ban ban;
}
