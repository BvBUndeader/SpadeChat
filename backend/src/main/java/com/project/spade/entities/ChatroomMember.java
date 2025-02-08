package com.project.spade.entities;


import com.project.spade.enums.MemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chatroom_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomMember {

    @EmbeddedId
    private ChatMemberComposite id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("chatroomId")
    @JoinColumn(name = "chatroom_id", nullable = false)
    private Chatroom chatroom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberRole role = MemberRole.MEMBER;
}
