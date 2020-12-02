import { Injectable } from '@angular/core';
import { ChatUser } from '../models/chat-user.model';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private currentUser: ChatUser;

    public setCurrentUser(user: ChatUser) {
        this.currentUser = user;
    }

    public getCurrentUser(): ChatUser {
        return this.currentUser;
    }
}
