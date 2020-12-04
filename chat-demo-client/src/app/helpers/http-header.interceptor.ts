
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { timeout } from 'rxjs/operators';

const API_TIMEOUT = 10000;

@Injectable({
    providedIn: 'root'
})
export class HttpHeaderInterceptor implements HttpInterceptor {
    constructor() {
    }

    // set common headers
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (!request.headers.has('Accept')) {
            request = request.clone({ headers: request.headers.set('Accept', 'application/json') });
        }

        if (!request.headers.has('Content-Type')) {
            request = request.clone({ headers: request.headers.set('Content-Type', 'application/json') });
        }

        // return next.handle(request);
        return next.handle(request).pipe(timeout(API_TIMEOUT)); // Set a timeout for the requests;
    }
}
