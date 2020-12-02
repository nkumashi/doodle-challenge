import { Type, inject, InjectFlags } from '@angular/core';

export class RootInjectorGuard {
    constructor(type: Type<any>) {
        // tslint:disable-next-line:no-bitwise
        const parent = inject(type, InjectFlags.Optional | InjectFlags.SkipSelf);

        if (parent) {
            throw Error(`[${type}]: trying to create multiple instances,
        but this service should be a singleton.`);
        }
    }
}
