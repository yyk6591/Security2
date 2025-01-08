package org.spring.security2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.spring.security2.common.BasicTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sec_reply_tb2")
public class ReplyEntity extends BasicTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="reply_id")
    private Long id;

    @Column(nullable = false)
    private String replyTitle;

    @Column(nullable = false)
    private String replyContent;

    @Column(nullable = false)
    private String replyNickName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;


}
