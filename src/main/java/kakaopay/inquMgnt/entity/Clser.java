package kakaopay.inquMgnt.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_CLSER")
public class Clser {
    @Id
    @Column(name = "clserId", nullable = false)
    private String clserId;

    @Column(nullable = false)
    private String clserNm;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime inputDthms;
}
