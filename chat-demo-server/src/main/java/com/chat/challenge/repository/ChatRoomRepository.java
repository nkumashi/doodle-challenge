package com.chat.challenge.repository;

import com.chat.challenge.model.ChatRoom;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	Optional<ChatRoom> findByRoomName(String roomname);
}
