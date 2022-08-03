package kakaopay.inquMgnt.repository;

import kakaopay.inquMgnt.entity.Inqu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InquRepository extends JpaRepository<Inqu, Long> {

    @Transactional
    @Modifying
    @Query("update Inqu i set i.clserId = :clserId, i.asmtDthms = :asmtDthms where i.inquId = :inquId")
    int updateClsId(@Param("clserId") String clserId, @Param("asmtDthms") LocalDateTime asmtDthms, @Param("inquId") Long inquId);
    /*
    고객id로 문의내역 조회
    @param ctmId 고객id
     */
    List<Inqu> findByUserId(String userId);

    /*
    게시판id로 문의내역 조회
    @param inquId 문의id
     */
    List <Inqu> findByInquId(Long inquId);

    /**
     * 상담원id로 문의내역 조회
     */
    List <Inqu> findByClserIdAndCmplYn(String clserId, String cmplYn);

    @Transactional
    @Modifying
    @Query("update Inqu i set i.rplCn = :rplCn, i.cmplYn = :cmplYn where i.inquId = :inquId")
    int updateRplCn(@Param("rplCn") String rplCn, @Param("cmplYn") String cmplYn, @Param("inquId") Long inquId);


}
