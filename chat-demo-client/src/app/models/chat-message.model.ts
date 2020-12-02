import { ChatUser } from './chat-user.model';

export class ChatMessage {
    sentByUser: ChatUser;
    content = '';
    createdAt = '';
}
