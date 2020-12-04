package com.chat.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chat.challenge.model.ChatUser;
import com.chat.challenge.repository.ChatUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatUserRepositoryTests {
	@Autowired
    private ChatUserRepository chatUserRepository;

	private ChatUser user = new ChatUser("Naveen");
	
	@Before
    public void init() {
		chatUserRepository.deleteAll();
		chatUserRepository.save(user);
    }

	@After
    public void cleanup() {
		chatUserRepository.delete(user);
	}
	
	@Test
    public void testCountAllUsers() {
        List<ChatUser> users = chatUserRepository.findAll();
        assertEquals(1, users.size());
    }
	
	@Test
    public void testFindUserByNameShouldSucceed() {
		Optional<ChatUser> user = chatUserRepository.findByUsername("Naveen");
        assertTrue(user.isPresent());
    }
	
	@Test
    public void testFindUserByNameShouldFail() {
		Optional<ChatUser> user = chatUserRepository.findByUsername("Nave");
		assertTrue(!user.isPresent());
    }
}
