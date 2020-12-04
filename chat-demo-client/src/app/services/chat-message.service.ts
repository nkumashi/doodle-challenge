import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const FETCH_MESSAGES_API_URL = '/api/latestChatMessages';

@Injectable({
    providedIn: 'root'
})
export class ChatMessageService {
    constructor(private http: HttpClient) { }

    fetchMessages(limit: number = 10): Observable<any> {
        return this.http.get(environment.baseURL + FETCH_MESSAGES_API_URL + '?' + '&limit=' + limit);
    }
}
