package com.zerobase.Fintech.service;

import static com.zerobase.Fintech.exception.ErrorCode.ALREADY_REGISTER_USER;
import static com.zerobase.Fintech.exception.ErrorCode.ALREADY_VERIFY;
import static com.zerobase.Fintech.exception.ErrorCode.EXPIRE_CODE;
import static com.zerobase.Fintech.exception.ErrorCode.USER_NOT_FOUND;
import static com.zerobase.Fintech.exception.ErrorCode.WRONG_VERIFICATION;

import com.zerobase.Fintech.jwt.dto.JwtDto;
import com.zerobase.Fintech.dto.SignUpForm;
import com.zerobase.Fintech.dto.UserDto;
import com.zerobase.Fintech.entity.User;
import com.zerobase.Fintech.exception.AccountException;
import com.zerobase.Fintech.jwt.config.JwtTokenProvider;
import com.zerobase.Fintech.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;

  public User signUp(SignUpForm form){
    return userRepository.save(User.from(form));
  }

  @Transactional
  public UserDto userSignUp(SignUpForm form){
    //이메일 존재 여부 체크
    if (userRepository.existsByEmail(form.getEmail())){
      throw new AccountException(ALREADY_REGISTER_USER);
    }
    return UserDto.from(signUp(form));
  }

  @Transactional
  public void verifyEmail(String email, String code){
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new AccountException(USER_NOT_FOUND));
    if (user.isVerify()){
      throw new AccountException(ALREADY_VERIFY);
    }else if (!user.getVerificationCode().equals(code)){
      throw new AccountException(WRONG_VERIFICATION);
    }else if (user.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
      throw new AccountException(EXPIRE_CODE);
    }
    user.verificationSuccess(true);
  }

  public Optional<User> findValidUser(String email, String password){
    return userRepository.findByEmail(email).stream().filter(
        user -> user.getPassword().equals(password) && user.isVerify()).findFirst();
  }

  @Transactional
  public LocalDateTime validateEmail(Long customerId, String verificationCode) {
    Optional<User> optionalCustomer = userRepository.findById(customerId);
    if (optionalCustomer.isPresent()) {
      User u = optionalCustomer.get();
      u.setVerificationCode(verificationCode, LocalDateTime.now().plusDays(1));
      return u.getVerifyExpiredAt();
    }
    throw new AccountException(USER_NOT_FOUND);
  }




}
