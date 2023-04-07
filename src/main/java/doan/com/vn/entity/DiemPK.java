package doan.com.vn.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiemPK implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Column(name = "ma_hs")
    private String maHS;

    @Column(name = "ma_mon")
    private String maMon;

    @Override
    public int hashCode() {
        return Objects.hash(maHS, maMon);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DiemPK other = (DiemPK) obj;
        return Objects.equals(maHS, other.maHS)
                && Objects.equals(maMon, other.maMon);
    }

}
