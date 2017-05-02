import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KbldemoSharedModule } from '../../shared';
import {
    FundService,
    FundPopupService,
    FundComponent,
    FundDetailComponent,
    FundDialogComponent,
    FundPopupComponent,
    FundDeletePopupComponent,
    FundDeleteDialogComponent,
    fundRoute,
    fundPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fundRoute,
    ...fundPopupRoute,
];

@NgModule({
    imports: [
        KbldemoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FundComponent,
        FundDetailComponent,
        FundDialogComponent,
        FundDeleteDialogComponent,
        FundPopupComponent,
        FundDeletePopupComponent,
    ],
    entryComponents: [
        FundComponent,
        FundDialogComponent,
        FundPopupComponent,
        FundDeleteDialogComponent,
        FundDeletePopupComponent,
    ],
    providers: [
        FundService,
        FundPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbldemoFundModule {}
