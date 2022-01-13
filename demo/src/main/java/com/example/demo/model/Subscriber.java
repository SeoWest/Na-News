package com.example.demo.model;

import com.example.demo.configuration.JwtTokenUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "subscriber_info")
@DynamicInsert
@DynamicUpdate
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "group_title")
    private String group_title;

    @Column(name = "create_name")
    private String create_name;

    public Subscriber() { super(); }

    public Subscriber(Integer id, String name, String email, String group_title, String create_name) {
        super();
        this.id=id;
        this.name = name;
        this.email = email;
        this.group_title = group_title;
        this.create_name = create_name;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }

    public String getemail() {
        return email;
    }
    public void setemail(String email) {
        this.email = email;
    }

    public String getgroup_title() {
        return group_title;
    }
    public void setgroup_title(String group_title) {
        this.group_title = group_title;
    }

    public String getCreate_name() { return create_name; }
    public void setCreate_name(String create_name) { this.create_name = create_name; }

    public String toString() {
        return "Group [id=" + id + ", name=" + name + ", email=" + email + ", group_title=" + group_title + ", create_name=" + create_name + "]";
    }
}
