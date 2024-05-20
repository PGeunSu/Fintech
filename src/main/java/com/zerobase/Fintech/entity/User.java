package com.zerobase.Fintech.entity;

import static com.zerobase.Fintech.type.Authority.*;

import com.zerobase.Fintech.dto.SignUpForm;
import com.zerobase.Fintech.type.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class User implements UserDetails {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(unique = true)
  private String email;
  private String password;
  private String name;

  @Enumerated(EnumType.STRING)
  private Authority role;

  //인증
  private boolean verify;
  private LocalDateTime verifyExpiredAt;
  private String verificationCode;

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime modifiedAt;

  public void setVerificationCode(String verificationCode, LocalDateTime verifyExpiredAt){
    this.verificationCode = verificationCode;
    this.verifyExpiredAt = verifyExpiredAt;
  }

  public void verificationSuccess(boolean verify){
    this.verify = verify;
  }

  public static User from(SignUpForm form){
    return User.builder()
        .email(form.getEmail())
        .name(form.getName())
        .password(form.getPassword())
        .role(ROLE_USER)
        .verify(false)
        .build();
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> roles = new ArrayList<>();
    roles.add(new SimpleGrantedAuthority(this.role.toString()));
    return roles;
  }

  @Override
  public String getUsername() {
    return "";
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}

