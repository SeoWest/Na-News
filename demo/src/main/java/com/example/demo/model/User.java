package com.example.demo.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "member_info")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "auth")
    private String auth;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "agency")
    private String agency;

    @Column(name = "message")
    private String message;

    @Builder
    public User(String email, String password, String auth, String phone, String name, String agency, String message) {
        this.email = email;
        this.password = password;
        this.auth = auth;
        this.phone = phone;
        this.name = name;
        this.agency = agency;
        this.message = message;
    }

    // 사용자의 권한을 콜렉션 형태로 반환
    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return email;
    }
    //public void setUsername(String email) { this.email = email; } // 이윤희 마이페이지 수정

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }
    //public void setPassword(String password) {this.password = password;} // 이윤희 마이페이지 수정

    public String getPhone() {
        return phone;
    }
    /*public void setPhone(String phone) {
        this.phone = phone;
    }*/

    public String getName() { return name; }
    //public void setName(String name) { this.name = name; }

    public String getAgency() { return agency; }
    //public void setAgency(String agency) { this.agency = agency; }

    public String getMessage() { return message; }
    //public void setMessage(String message) { this.message = message; }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }


    public String toString() { // 이윤희 마이페이지 수정
        return "User [email=" + email + ", password=" + password + ", phone=" + phone + ", name=" + name + ", message=" + message + "]";
    }
}
