package hibernate_basic;

import javax.persistence.*;

@Entity
@Table(name= "identity_member_detail")
public class IdentityMemberDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long memberPk;

    private String detail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberPk() {
        return memberPk;
    }

    public void setMemberPk(long memberPk) {
        this.memberPk = memberPk;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
