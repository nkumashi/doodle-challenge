import { Injectable } from '@angular/core';
import { Overlay } from '@angular/cdk/overlay';
import { ComponentPortal } from '@angular/cdk/portal';
import { MatSpinner } from '@angular/material/progress-spinner';

@Injectable({
    providedIn: 'root',
})
export class SpinnerService {
    spinner = this.overlay.create({
        hasBackdrop: true,
        positionStrategy: this.overlay
            .position().global().centerHorizontally().centerVertically()
    });

    constructor(
        private overlay: Overlay) {
    }

    startSpinner() {
        try {
            this.spinner.attach(new ComponentPortal(MatSpinner));
        } catch (error) {
            this.spinner.detach();

            this.spinner = this.overlay.create({
                hasBackdrop: true,
                positionStrategy: this.overlay
                    .position().global().centerHorizontally().centerVertically()
            });
            this.spinner.attach(new ComponentPortal(MatSpinner));
        }
    }

    stopSpinner() {
        this.spinner.detach();
    }
}
