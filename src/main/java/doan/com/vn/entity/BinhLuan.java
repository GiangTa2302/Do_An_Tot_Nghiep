package doan.com.vn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Binh_Luan")
@Data
@EqualsAndHashCode(callSuper=false)
public class BinhLuan extends Base{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_bl")
    private Integer maBL;
    
    @Column(name = "noi_dung", columnDefinition = "NVARCHAR(2000)")
    private String noiDung;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", referencedColumnName = "account")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_bg", referencedColumnName = "ma_bg")
    private BaiGiang baiGiang;
}
