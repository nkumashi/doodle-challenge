package com.chat.challenge.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.chat.challenge.model.ChatUser;

@Repository
public interface ChatUserRepository extends MongoRepository<ChatUser, String> {

	Optional<ChatUser> findByUsername(String username);
}
