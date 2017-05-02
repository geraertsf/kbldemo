import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KbldemoSharedModule } from '../../shared';
import {
    VniHistoryService,
    VniHistoryPopupService,
    VniHistoryComponent,
    VniHistoryDetailComponent,
    VniHistoryDialogComponent,
    VniHistoryPopupComponent,
    VniHistoryDeletePopupComponent,
    VniHistoryDeleteDialogComponent,
    vniHistoryRoute,
    vniHistoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...vniHistoryRoute,
    ...vniHistoryPopupRoute,
];

@NgModule({
    imports: [
        KbldemoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VniHistoryComponent,
        VniHistoryDetailComponent,
        VniHistoryDialogComponent,
        VniHistoryDeleteDialogComponent,
        VniHistoryPopupComponent,
        VniHistoryDeletePopupComponent,
    ],
    entryComponents: [
        VniHistoryComponent,
        VniHistoryDialogComponent,
        VniHistoryPopupComponent,
        VniHistoryDeleteDialogComponent,
        VniHistoryDeletePopupComponent,
    ],
    providers: [
        VniHistoryService,
        VniHistoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbldemoVniHistoryModule {}
