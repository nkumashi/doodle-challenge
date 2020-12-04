import { Component, ElementRef, OnInit, QueryList, ViewChild, ViewChildren, AfterViewChecked } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

import { UserService } from 'src/app/services/user.service';
import { ChatMessageDTO } from 'src/app/models/chat-message.dto';
import { ChatMessageService } from 'src/app/services/chat-message.service';
import { DomSanitizer } from '@angular/platform-browser';
import { SecurityContext } from '@angular/core';
import { SpinnerService } from 'src/app/services/spinner-service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';

const WEB_SOCKET_SERVER_URL = '/ws';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, AfterViewChecked {
  public chatMessages: Array<ChatMessageDTO> = [];
  private stompClient;
  public form: FormGroup;

  @ViewChild('scrollFrame') private scrollFrame: ElementRef;
  // @ViewChildren('scrollFrame') frame: QueryList<ElementRef>;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private sanitizer: DomSanitizer,
    private toastr: ToastrService,
    private spinnerService: SpinnerService,
    private chatMessageService: ChatMessageService
  ) {
    this.initializeWebSocketConnection();
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      message: ['']
    });

    // fetch the latest messages
    try {
      this.spinnerService.startSpinner();
      this.chatMessageService.fetchMessages().subscribe(
        data => {
          console.log('Received messages from server: ' + JSON.stringify(data));

          this.spinnerService.stopSpinner();
          data.forEach(element => {
            this.chatMessages.push(element);
          });
        },
        err => {
          this.spinnerService.stopSpinner();
          // this.toastr.error('Could not fetch the latest messages.');
          console.log('Error while fetching messages: ' + JSON.stringify(err));
        }
      );
    } catch (err) {
      this.spinnerService.stopSpinner();
      // this.toastr.error('Could not fetch the latest messages.');
      console.log('Error while fetching messages: ' + JSON.stringify(err));
    }
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.scrollFrame.nativeElement.scrollTop = this.scrollFrame.nativeElement.scrollHeight;
    } catch (err) {
      console.log('Error while scrolling: ' + JSON.stringify(err));
    }
  }

  initializeWebSocketConnection() {
    const ws = new SockJS(environment.baseURL + WEB_SOCKET_SERVER_URL);
    this.stompClient = Stomp.over(ws);
    const that = this;

    this.stompClient.connect({}, (frame) => {
      console.log('Connected to web socket server...');

      that.stompClient.subscribe('/topic/public', (message) => {
        this.onMessageReceived(message);
      });
    }, (frame) => {
      console.log('Problem with our web socket connection...');

      this.toastr.error('Oops! The server has stopped responding. Try again later.');
      this.authService.unRegisterUser();
      this.stompClient.disconnect();
      this.router.navigateByUrl('/');
    });
  }

  onMessageReceived(message) {
    if (message.body) {
      console.log(`Received message from server: ${message.body}`);

      const chatMessage = JSON.parse(message.body) as ChatMessageDTO;
      this.chatMessages.push(chatMessage);
      this.scrollToBottom();
    }
  }

  onSubmit(message) {
    const sanitizedMessage = this.sanitizer.sanitize(SecurityContext.HTML, message);
    if (sanitizedMessage.trim() !== '') {
      const chatMessage = new ChatMessageDTO();
      chatMessage.content = sanitizedMessage;
      chatMessage.sentByUser = this.userService.getCurrentUser();

      console.log('Sending message: ' + JSON.stringify(chatMessage));
      this.stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessage));
      this.form.reset();
    }
  }
}
