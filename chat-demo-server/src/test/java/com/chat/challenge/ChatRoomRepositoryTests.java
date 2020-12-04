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

import com.chat.challenge.model.ChatRoom;
import com.chat.challenge.repository.ChatRoomRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatRoomRepositoryTests {
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	private ChatRoom room = new ChatRoom("Dummy", "Dummy");
	
	@Before
    public void init() {
		chatRoomRepository.deleteAll();			
		chatRoomRepository.save(room);
    }
	
	@After
    public void cleanup() {
		chatRoomRepository.delete(room);
	}
	
	
	@Test
    public void testCountAllChatRooms() {
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        assertEquals(1, rooms.size());
    }
	
	@Test
    public void testFindRoomByNameShouldSucceed() {
		Optional<ChatRoom> room = chatRoomRepository.findByRoomName("Dummy");
        assertTrue(room.isPresent());
    }
	
	@Test
    public void testFindRoomByNameShouldFail() {
		Optional<ChatRoom> room = chatRoomRepository.findByRoomName("Dumm");
		assertTrue(!room.isPresent());
    }
}
