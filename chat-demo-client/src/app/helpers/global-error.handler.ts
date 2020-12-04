import { ErrorHandler, Inject, Injectable } from '@angular/core';
import * as uuid from 'uuid';

@Injectable({
    providedIn: 'root'
})
export class GlobalErrorHandler implements ErrorHandler {
    constructor() {
    }

    handleError(error: Error) {
        const err = {
            logId: uuid.v4(),
            applicationName: 'auth-user-app',
            timestamp: new Date(),
            errorStack: error.stack ? error.stack : '',
            errorMessage: error.message ? error.message : ''
        };

        // Log  the error
        console.log('GlobalErrorHandler: Error: ' + JSON.stringify(err));

        // Optionally send it to your back-end API

        // Re-throw the error
        throw error;
    }
}
