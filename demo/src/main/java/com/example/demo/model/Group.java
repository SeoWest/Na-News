package com.example.demo.model;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "group_info")
@DynamicInsert
@DynamicUpdate
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer group_code;

    @Column(name = "group_title")
    private String group_title;

    @Column(name = "create_name")
    private String create_name;


    public Group() {
        super();
    }

    public Group(Integer group_code, String group_title, String create_name) {
        super();
        this.group_code = group_code;
        this.group_title = group_title;
        this.create_name = create_name;
    }

    public Integer getGroup_code() {
        return group_code;
    }

    public void setGroup_code(Integer group_code) {
        this.group_code = group_code;
    }

    public String getGroup_title() {
        return group_title;
    }

    public void setGroup_title(String group_title) {
        this.group_title = group_title;
    }

    public String getCreate_name() { return create_name; }
    public void setCreate_name(String create_name) { this.create_name = create_name; }


    @Override
    public String toString() {
        return "Group [group_code=" + group_code + ", group_title=" + group_title + ", create_name=" + create_name + "]";
    }
// ---Getter/Setter ---

}