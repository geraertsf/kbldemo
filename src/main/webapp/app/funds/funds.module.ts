import {NgModule} from '@angular/core';
//import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {FUNDS_ROUTE} from './funds.route';
import {RouterModule} from '@angular/router';
import {FundListComponent} from './fund-list.component';
import {KbldemoSharedModule} from '../shared/shared.module';
/**
 * Created by Francois Geraerts on 4/05/17.
 */
@NgModule({
    imports: [
        KbldemoSharedModule,
        RouterModule.forRoot([FUNDS_ROUTE], {useHash: true})
    ],
    declarations: [
        FundListComponent,
    ],
    entryComponents: [],
    providers: []
    // ,
    // schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FundsModule {
}
