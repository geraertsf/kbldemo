import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { VniHistoryComponent } from './vni-history.component';
import { VniHistoryDetailComponent } from './vni-history-detail.component';
import { VniHistoryPopupComponent } from './vni-history-dialog.component';
import { VniHistoryDeletePopupComponent } from './vni-history-delete-dialog.component';

import { Principal } from '../../shared';

export const vniHistoryRoute: Routes = [
  {
    path: 'vni-history',
    component: VniHistoryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.vniHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'vni-history/:id',
    component: VniHistoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.vniHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vniHistoryPopupRoute: Routes = [
  {
    path: 'vni-history-new',
    component: VniHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.vniHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'vni-history/:id/edit',
    component: VniHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.vniHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'vni-history/:id/delete',
    component: VniHistoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'kbldemoApp.vniHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
