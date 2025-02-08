package com.project.spade.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMemberComposite implements Serializable {
    private int chatroomId;
    private int userId;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ChatMemberComposite that = (ChatMemberComposite) o;
        return Objects.equals(chatroomId, that.chatroomId) &&
                Objects.equals(userId, this.userId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(chatroomId, userId);
    }
}
