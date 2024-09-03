package com.alibou.security.auth;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  private Long id ;
  private String email;
  private String password;
  private String firstname ;
  private String lastname ;
  private String role  ;
}
