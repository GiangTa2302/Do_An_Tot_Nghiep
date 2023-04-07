package doan.com.vn.entity;

import java.util.Set;

import javax.persistence.CascadeType;
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
public class HocLuc extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hl")
    private int maHL;
    
    @Column(name = "ten_hl")
    private String tenHL;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hocLuc")
    private Set<HocSinh> hocSinhs;
}
