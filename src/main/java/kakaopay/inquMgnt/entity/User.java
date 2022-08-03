package kakaopay.inquMgnt.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_USER")
public class User {
    @Id
    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime inputDthms;
}
