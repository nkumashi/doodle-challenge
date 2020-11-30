import { Component } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private serverUrl = '/ws';
  public title = 'WebSockets chat';
  private stompClient;

  constructor() {
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({}, (frame) => {
      // Tell your username to the server
      that.stompClient.send('/app/chat.addUser',
        {},
        JSON.stringify({ senderName: 'naveenkumashi', type: 'JOIN' })
      );

      that.stompClient.subscribe('/topic/public', (message) => {
        if (message.body) {
          $('.chat').append('<div class="message">' + message.body + '</div>');
        } else if (message.body && message.body.content !== null) {
          $('.chat').append('<div class="message">' + message.body + '</div>');
        }
        console.log(message.body);
      });
    });
  }

  sendMessage(message) {
    this.stompClient.send('/app/chat.sendMessage', {}, message);
    $('#input').val('');
  }
}
