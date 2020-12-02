
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import * as uuid from 'uuid';
import 'rxjs/add/observable/throw';
import { RootInjectorGuard } from './root-injector.guard';

@Injectable({
    providedIn: 'root'
})
export class HttpErrorInterceptor extends RootInjectorGuard implements HttpInterceptor {
    constructor() {
        super(HttpErrorInterceptor);
    }

    // the main interceptor method where all the action happens
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    console.log('HttpAuthInterceptor: event: ', event.statusText);
                }
                return event;
            }),
            catchError((error: HttpErrorResponse) => {
                const err = {
                    logId: uuid.v4(),
                    applicationName: 'auth-user-app',
                    timestamp: new Date(),
                    errorReason: error && error.error ? error.error : '',
                    errorStatus: error && error.status ? error.status : ''
                };

                // Log  the error
                console.log('HttpErrorInterceptor: Error: ' + JSON.stringify(err));

                // Optionally send it to your back-end API

                // Re-throw the error
                throw error;
            })
        );
    }
}
