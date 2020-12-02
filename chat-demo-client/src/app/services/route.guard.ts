import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root'
})
export class RouteGuardService implements CanActivate {
    constructor(
        public auth: AuthService,
        public router: Router
    ) { }

    canActivate(): boolean {
        if (!this.auth.isUserRegistered()) {
            // user not registered yet, go back to registration page
            this.router.navigateByUrl('/');
            return false;
        }
        return true;
    }
}
