package doan.com.vn.entity;

import java.util.Objects;
import java.util.Set;

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
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HocSinh extends Person{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "hs-generator", 
      parameters = @Parameter(name = "prefix", value = "HS"), 
      strategy = "doan.com.vn.entity.GenerateIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hs-generator")
    @Column(name = "ma_hs")
    private String maHS;
    
    @Column(name = "anh_hs")
    private String anhHS;
    
    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_ph", referencedColumnName = "ma_ph")
    private PhuHuynh phuHuynh;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ten_lop", referencedColumnName = "ten_lop")
    private Lop lop;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hl", referencedColumnName = "ma_hl")
    private HocLuc hocLuc;
    
    @Column(name = "ton_giao", columnDefinition = "NVARCHAR(200)")
    private String tonGiao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_dan_toc", referencedColumnName = "ma_dan_toc")
    private DanToc danToc;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_the_bhyt", referencedColumnName = "ma_the", unique = true)
    private TheBHYT theBHYT;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hocSinh")
    private Set<Diem> diems;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        HocSinh other = (HocSinh) obj;
        return Objects.equals(maHS, other.maHS);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(maHS);
        return result;
    }

   
}
