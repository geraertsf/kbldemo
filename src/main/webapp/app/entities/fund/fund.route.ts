import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FundComponent } from './fund.component';
import { FundDetailComponent } from './fund-detail.component';
import { FundPopupComponent } from './fund-dialog.component';
import { FundDeletePopupComponent } from './fund-delete-dialog.component';

import { Principal } from '../../shared';

export const fundRoute: Routes = [
  {
    path: 'fund',
    component: FundComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.fund.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'fund/:id',
    component: FundDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.fund.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fundPopupRoute: Routes = [
  {
    path: 'fund-new',
    component: FundPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.fund.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'fund/:id/edit',
    component: FundPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.fund.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'fund/:id/delete',
    component: FundDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.fund.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
