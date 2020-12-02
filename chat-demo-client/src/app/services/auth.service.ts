import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const REGISTER_API = '/api/user';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

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

    register(user): Observable<any> {
        return this.http.post(REGISTER_API, {
            username: user.username
        }, httpOptions);
    }
}
