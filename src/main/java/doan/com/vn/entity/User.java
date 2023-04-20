package doan.com.vn.entity;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "[User]")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    private String username;

//    @Pattern(regexp = "")
    private String password;

    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = this.roles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (roleName.equals(role.getRoleName())) {
                return true;
            }
        }

        return false;
    }

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @OneToOne(mappedBy = "user")
    private HocSinh hocSinh;

    @OneToOne(mappedBy = "user")
    private GiaoVien giaoVien;
}
