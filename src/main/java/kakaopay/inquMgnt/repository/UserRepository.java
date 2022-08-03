package kakaopay.inquMgnt.repository;

import kakaopay.inquMgnt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /*
    고객이 입력한 id가 존재하는지 확인
    @param ctmId 고객id
     */
    List<User> findByUserId(String userId);
}
