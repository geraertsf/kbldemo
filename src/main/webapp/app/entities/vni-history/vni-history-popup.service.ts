import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { VniHistory } from './vni-history.model';
import { VniHistoryService } from './vni-history.service';
@Injectable()
export class VniHistoryPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private vniHistoryService: VniHistoryService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.vniHistoryService.find(id).subscribe((vniHistory) => {
                vniHistory.date = this.datePipe
                    .transform(vniHistory.date, 'yyyy-MM-ddThh:mm');
                this.vniHistoryModalRef(component, vniHistory);
            });
        } else {
            return this.vniHistoryModalRef(component, new VniHistory());
        }
    }

    vniHistoryModalRef(component: Component, vniHistory: VniHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vniHistory = vniHistory;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
