import { Injectable } from '@angular/core';
import { ChatUserDTO } from '../models/chat-user.dto';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private currentUser: ChatUserDTO;

    public setCurrentUser(user: ChatUserDTO) {
        this.currentUser = user;
    }

    public getCurrentUser(): ChatUserDTO {
        return this.currentUser;
    }
}
