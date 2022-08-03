package kakaopay.inquMgnt.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ",
        initialValue = 1,
        allocationSize = 1)
@Table(name = "TB_INQU")
public class Inqu implements Serializable {
    @Id
    @Column(name = "inquId", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ")
    private Long inquId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String inquTitl;

    @Column(nullable = false)
    private String inquCn;

    @Column
    private String clserId;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime inputDthms;

    @Column
    private LocalDateTime asmtDthms;

    @ColumnDefault("'N'")
    private String cmplYn;

    @Column
    private LocalDateTime rplDthms;

    @Column
    private String rplCn;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        InquReplyId inquReplyId = (InquReplyId) o;
//        return inquId.equals(inquReplyId.getInquId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(inquId);
//    }
}