package hibernate_basic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@SequenceGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        allocationSize = 1
//)
@Table(name= "identity_member")
public class IdentityMember {
    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IdentityMemberDetail> memberDetailList = new ArrayList<>();

    public List<IdentityMemberDetail> getMemberDetailList() {
        return memberDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
