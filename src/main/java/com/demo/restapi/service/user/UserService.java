package com.demo.restapi.service.user;

import com.demo.restapi.common.advice.exception.CUserNotFoundException;
import com.demo.restapi.entity.User;
import com.demo.restapi.repository.security.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public User save(User user) { return userJpaRepository.save(user); }

    /**
     * 사용자 목록 조회
     * @return
     */
    public List<User> getAll() {
        return userJpaRepository.findAll();
    }

    /**
     * 사용자 정보 조회
     * @param uid
     * @return
     */
    public User getOne(String uid) {
        return userJpaRepository.findByUid(uid).orElseThrow(CUserNotFoundException::new);
    }


    /**
     * 인증된 사용자 정보 조회
     * @return
     */
    public User getAuthOne() {
        // SecurityContext 에서 인증받은 사용자 정보 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        return userJpaRepository.findByUid(uid).orElseThrow(CUserNotFoundException::new);
    }

    /**
     * Social 사용자 정보 조회
     * @param uid
     * @param provider
     * @return
     */
    public Optional<User> getSocialOne(String uid, String provider) {
        return userJpaRepository.findByUidAndProvider(uid, provider);
    }

    /**
     * 사용자 정보 수정
     * @param name
     * @return
     */
    public User update(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        User user = userJpaRepository.findByUid(uid).orElseThrow(CUserNotFoundException::new);
        user.setName(name);

        return userJpaRepository.save(user);
    }

    /**
     * 사용자 정보 삭제
     * @param msrl
     */
    public void delete(long msrl) {
        userJpaRepository.deleteById(msrl);
    }
}
