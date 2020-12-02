import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

import { UserService } from 'src/app/services/user.service';
import { ChatMessage } from 'src/app/models/chat-message.model';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  public chatMessages: Array<ChatMessage> = [];

  private serverUrl = '/ws';
  public title = 'WebSockets chat';
  private stompClient;
  public form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService
  ) {
    this.initializeWebSocketConnection();
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      message: ['']
    });
  }

  initializeWebSocketConnection() {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;

    this.stompClient.connect({}, (frame) => {
      console.log('Connected to web socket server...');

      that.stompClient.subscribe('/topic/public', (message) => {
        this.onMessageReceived(message);
      });
    });
  }

  onMessageReceived(message) {
    if (message.body) {
      console.log(`Received message from server: ${message.body}`);

      const chatMessage = JSON.parse(message.body) as ChatMessage;
      this.chatMessages.push(chatMessage);
    }
  }

  onSubmit(message) {
    if (message.trim() !== '') {
      const chatMessage = new ChatMessage();
      chatMessage.content = message;
      chatMessage.sentByUser = this.userService.getCurrentUser();

      console.log('Sending message: ' + JSON.stringify(chatMessage));
      this.stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessage));
      this.form.reset();
    }
  }
}
