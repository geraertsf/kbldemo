import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { VniHistory } from './vni-history.model';
import { VniHistoryService } from './vni-history.service';

@Component({
    selector: 'jhi-vni-history-detail',
    templateUrl: './vni-history-detail.component.html'
})
export class VniHistoryDetailComponent implements OnInit, OnDestroy {

    vniHistory: VniHistory;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private vniHistoryService: VniHistoryService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['vniHistory']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVniHistories();
    }

    load(id) {
        this.vniHistoryService.find(id).subscribe((vniHistory) => {
            this.vniHistory = vniHistory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVniHistories() {
        this.eventSubscriber = this.eventManager.subscribe('vniHistoryListModification', (response) => this.load(this.vniHistory.id));
    }
}
