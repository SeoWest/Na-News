package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_info")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /*@Column(name = "member_email")
    private String member_email;*/
    @Column(name = "pw")
    private String pw;

    @Column(name = "mail")
    private String mail;

    public Member() {
        super();
    }

    public Member(String id, String pw, String mail) {
        super();
        this.id = id;
        this.pw = pw;
        this.mail = mail;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPw() { return pw; }
    public void setPw(String pw) { this.pw = pw; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    @Override
    public String toString() {
        return "Member [id=" + id + ", pw=" + pw + ", mail=" + mail + "]";
    }
}
