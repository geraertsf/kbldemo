import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Fund } from './fund.model';
import { FundPopupService } from './fund-popup.service';
import { FundService } from './fund.service';

@Component({
    selector: 'jhi-fund-delete-dialog',
    templateUrl: './fund-delete-dialog.component.html'
})
export class FundDeleteDialogComponent {

    fund: Fund;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private fundService: FundService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['fund']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fundService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fundListModification',
                content: 'Deleted an fund'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fund-delete-popup',
    template: ''
})
export class FundDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fundPopupService: FundPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.fundPopupService
                .open(FundDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
