package com.chat.challenge;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.chat.challenge.model.ChatMessage;
import com.chat.challenge.repository.ChatMessageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatMessageRepositoryTests {
	@Autowired
	private ChatMessageRepository chatMessageRepository;
	
	private ChatMessage message1 = new ChatMessage();
	private ChatMessage message2 = new ChatMessage();
	
	@Before
    public void init() {
		chatMessageRepository.deleteAll();
		
		LocalDateTime now = LocalDateTime.now();
		message1.setContent("Dummy text.");		
		message1.setChatRoom(null);
		message1.setCreatedAt(now);
		message1.setSentByUser(null);
		
		message2.setContent("Dummy text.");		
		message2.setChatRoom(null);
		message2.setCreatedAt(now);
		message2.setSentByUser(null);
		
		chatMessageRepository.save(message1);
		chatMessageRepository.save(message2);
    }
	
	@After
    public void cleanup() {
		chatMessageRepository.delete(message1);
		chatMessageRepository.delete(message1);
	}
	
	@Test
    public void testCountAllChatMessages() {
        List<ChatMessage> messages = chatMessageRepository.findAll();
        assertEquals(2, messages.size());
    }
	
	@Test
	public void testFetchFirstPage() {
		int page = 0;
		int size = 1;
		Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
		List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
		Page<ChatMessage> pages = chatMessageRepository.findAll(paging);
		chatMessages = pages.getContent();
		assertEquals(1, chatMessages.size());
	}
	
	@Test
	public void testFetchSecondPage() {
		int page = 1;
		int size = 1;
		Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
		List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
		Page<ChatMessage> pages = chatMessageRepository.findAll(paging);
		chatMessages = pages.getContent();
		assertEquals(1, chatMessages.size());
	}
	
	@Test
	public void testFetchTop10Records() {
		// should fetch all records we have right now
		List<ChatMessage> chatMessages = chatMessageRepository.findFirst10ByOrderByCreatedAtDesc();
		assertEquals(2, chatMessages.size());
	}
}
