import {Route} from '@angular/router';
import {FundListComponent} from './fund-list.component';
/**
 * Created by Francois Geraerts on 4/05/17.
 */
export const FUNDS_ROUTE: Route = {
    path: 'funds',
    component: FundListComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'fund.title'
    }
};
