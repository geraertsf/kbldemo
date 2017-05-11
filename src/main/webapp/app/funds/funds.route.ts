import {Routes} from '@angular/router';
import {FundListComponent} from './fund-list.component';
import {FundVniComponent} from './fund-vni.component';
/**
 * Created by Francois Geraerts on 4/05/17.
 */
export const FUNDS_ROUTE: Routes = [
    {
        path: 'funds',
        component: FundListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fund.title'
        }
    },
    {
        path: 'funds-vni/:id',
        component: FundVniComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kbldemoApp.fund.home.title'
        }
    }
];
