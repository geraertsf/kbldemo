import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { VniHistory } from './vni-history.model';
import { VniHistoryPopupService } from './vni-history-popup.service';
import { VniHistoryService } from './vni-history.service';
import { Fund, FundService } from '../fund';

@Component({
    selector: 'jhi-vni-history-dialog',
    templateUrl: './vni-history-dialog.component.html'
})
export class VniHistoryDialogComponent implements OnInit {

    vniHistory: VniHistory;
    authorities: any[];
    isSaving: boolean;

    funds: Fund[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private vniHistoryService: VniHistoryService,
        private fundService: FundService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['vniHistory']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.fundService.query().subscribe(
            (res: Response) => { this.funds = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vniHistory.id !== undefined) {
            this.vniHistoryService.update(this.vniHistory)
                .subscribe((res: VniHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.vniHistoryService.create(this.vniHistory)
                .subscribe((res: VniHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: VniHistory) {
        this.eventManager.broadcast({ name: 'vniHistoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackFundById(index: number, item: Fund) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-vni-history-popup',
    template: ''
})
export class VniHistoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vniHistoryPopupService: VniHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.vniHistoryPopupService
                    .open(VniHistoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.vniHistoryPopupService
                    .open(VniHistoryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
