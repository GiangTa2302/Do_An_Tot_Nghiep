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
@Table(name = "Danh_Sach_Video")
@Data
@EqualsAndHashCode(callSuper=false)
public class DanhSachVideo extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_video")
    private Integer maVideo;
    
    private String urlVideo;
    
    @Column(name = "ten_video", columnDefinition = "NVARCHAR(1000)")
    private String tenVideo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_bg", referencedColumnName = "ma_bg")
    private BaiGiang baiGiang;
}
