package doan.com.vn.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Ban extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_ban")
    private int maBan;
    
    @Column(name = "ten_ban", columnDefinition = "NVARCHAR(100)")
    private String tenBan;
    
    @OneToMany(mappedBy = "ban")
    private Set<GiaoVien> giaoViens;
}
