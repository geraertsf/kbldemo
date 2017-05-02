import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KbldemoCategoryModule } from './category/category.module';
import { KbldemoSubCategoryModule } from './sub-category/sub-category.module';
import { KbldemoCurrencyModule } from './currency/currency.module';
import { KbldemoFundModule } from './fund/fund.module';
import { KbldemoVniHistoryModule } from './vni-history/vni-history.module';
import { KbldemoCountryModule } from './country/country.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        KbldemoCategoryModule,
        KbldemoSubCategoryModule,
        KbldemoCurrencyModule,
        KbldemoFundModule,
        KbldemoVniHistoryModule,
        KbldemoCountryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KbldemoEntityModule {}
