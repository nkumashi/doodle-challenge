import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';

import { ChatMessage } from 'src/app/models/chat-message.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-chat-bubble',
  templateUrl: './chat-bubble.component.html',
  styleUrls: ['./chat-bubble.component.css']
})
export class ChatBubbleComponent implements OnInit {
  @Input() chatMessage: ChatMessage;

  constructor(
    public userService: UserService
  ) { }

  ngOnInit(): void {
  }

}
