package doan.com.vn.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "dap_an")
@Data
@EqualsAndHashCode(callSuper = false)
public class DapAn extends Base implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ma_da")
    private String maDA;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_ch", referencedColumnName = "ma_ch")
    private CauHoi cauHoi;

    @Column(name = "noi_dung", columnDefinition = "NVARCHAR(2000)")
    private String noiDung;

    public DapAn(String maDA, CauHoi cauHoi, String noiDung) {
        super();
        this.maDA = maDA;
        this.cauHoi = cauHoi;
        this.noiDung = noiDung;
    }

    public DapAn() {
        super();
    }

    public DapAn(String maDA) {
        super();
        this.maDA = maDA;
    }

}
