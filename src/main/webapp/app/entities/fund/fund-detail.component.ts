import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Fund } from './fund.model';
import { FundService } from './fund.service';

@Component({
    selector: 'jhi-fund-detail',
    templateUrl: './fund-detail.component.html'
})
export class FundDetailComponent implements OnInit, OnDestroy {

    fund: Fund;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private fundService: FundService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['fund']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFunds();
    }

    load(id) {
        this.fundService.find(id).subscribe((fund) => {
            this.fund = fund;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFunds() {
        this.eventSubscriber = this.eventManager.subscribe('fundListModification', (response) => this.load(this.fund.id));
    }
}
