package doan.com.vn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thong_bao", columnDefinition = "NVARCHAR(1000)")
    private String thongBao;

    @Column(name = "thoi_gian")
    private Date thoiGian;

    public Notify(String thongBao, Date thoiGian) {
        super();
        this.thongBao = thongBao;
        this.thoiGian = thoiGian;
    }
}
