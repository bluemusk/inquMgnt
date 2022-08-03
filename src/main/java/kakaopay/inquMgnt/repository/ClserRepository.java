package kakaopay.inquMgnt.repository;

import kakaopay.inquMgnt.entity.Clser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClserRepository extends JpaRepository<Clser, String> {
    List<Clser> findByClserId(String clserId);

    List<Clser> findByClserIdAndPwd(String clserId, String pwd);

    @Query("select count(c) from Clser c where c.clserId = :clserId and c.pwd = :pwd")
    long listCnt(@Param("clserId") String clserId, @Param("pwd") String pwd);
}
