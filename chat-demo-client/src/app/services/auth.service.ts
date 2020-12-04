import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const REGISTER_API_URL = '/api/user';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private userRegistered = false;

    constructor(private http: HttpClient) { }

    public isUserRegistered() {
        return this.userRegistered === true;
    }

    public setUserRegistered(userRegistered: boolean) {
        this.userRegistered = userRegistered;
    }

    public unRegisterUser() {
        this.userRegistered = false;
    }

    register(user): Observable<any> {
        return this.http.post(environment.baseURL + REGISTER_API_URL, {
            username: user.username
        });
    }
}
