package com.accountbook.domain.entity;

import com.accountbook.dto.user.UserCreateRequest;
import com.accountbook.dto.user.UserUpdateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * User
 *
 * @author donggun
 * @since 2022/04/12
 */
@Entity
@Getter
@ToString(of = {"id", "password", "name", "email", "birthDate", "token", "expireDate", "setting"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class User extends BaseTimeInfo {

    @Id
    @Column(name = "USER_ID")
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDateTime birthDate;

    // 자동 로그인 대상 여부 판단
    private String token;
    private LocalDateTime expireDate;
    private String loginIp;

    // 사용자 설정
    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CustomSetting setting;

    // 생성자 메서드
    public static User createUser(UserCreateRequest request) {

        User user = new User();

        user.id = request.getId();
        user.password = request.getPassword();
        user.name = request.getName();
        user.email = request.getEmail();
        user.birthDate = request.getBirthDate();

        return user;
    }

    // 비즈니스 로직 메서드
    public void setSetting(CustomSetting setting) {
        this.setting = setting;
    }


    public void changeUser(UserUpdateRequest request) {

        if(StringUtils.hasText(request.getName())) {
            this.name = request.getName();
        }

        if(StringUtils.hasText(request.getEmail())) {
            this.email = request.getEmail();
        }
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public Boolean checkPwdUpdate (String password) {
        return password.equals(this.password);
    }

    public void changeSessionInfo(String token, LocalDateTime expireDate, String loginIp) {
        this.token = token;
        this.expireDate = expireDate;
        this.loginIp = loginIp;
    }
}
