package com.demo.restapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Proxy(lazy = false)
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long msrl;
    @Column(nullable = false, unique = true, length = 30)
    private String uid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 100)
    private String password;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 100)
    private String provider;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    // 회원 구분값인 [uid]로 변경
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    /**
     * 아래의 method 들은 Security 환경에서 사용하는 회원 상태값이지만,
     * 사용하지 않기 때문에 모두 "true" 설정
     * JSON 결과에 출력하지 않을 데이터는 "JsonProperty.Access.WRITE_ONLY" 설정
     *
     * - isAccountNonExpired : 계정이 만료 안되었는지
     * - isAccountNonLocked : 계정이 잠긴 상태인지
     * - isCredentialsNonExpired : 계정 비밀번호가 만료된 상태인지
     * - isEnabled : 계정이 사용 가능한 상태인지
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
