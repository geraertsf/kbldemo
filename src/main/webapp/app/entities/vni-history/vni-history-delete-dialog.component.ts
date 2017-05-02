import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { VniHistory } from './vni-history.model';
import { VniHistoryPopupService } from './vni-history-popup.service';
import { VniHistoryService } from './vni-history.service';

@Component({
    selector: 'jhi-vni-history-delete-dialog',
    templateUrl: './vni-history-delete-dialog.component.html'
})
export class VniHistoryDeleteDialogComponent {

    vniHistory: VniHistory;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private vniHistoryService: VniHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['vniHistory']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vniHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'vniHistoryListModification',
                content: 'Deleted an vniHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vni-history-delete-popup',
    template: ''
})
export class VniHistoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vniHistoryPopupService: VniHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.vniHistoryPopupService
                .open(VniHistoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
