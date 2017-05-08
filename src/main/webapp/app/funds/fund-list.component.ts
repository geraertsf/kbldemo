import {Component, OnInit} from '@angular/core';
import {AlertService, EventManager, JhiLanguageService, ParseLinks} from 'ng-jhipster';
import {FundService} from '../entities/fund/fund.service';
import {Fund} from '../entities/fund/fund.model';
import {Response} from '@angular/http';
import {ITEMS_PER_PAGE} from '../shared/constants/pagination.constants';
import {Principal} from '../shared/auth/principal.service';
import {Subscription} from 'rxjs/Subscription';
/**
 * Created by Francois Geraerts on 4/05/17.
 */
@Component({
    templateUrl: './fund-list.component.html'
})
export class FundListComponent implements OnInit {

    funds: Fund[];
    page: any;
    itemsPerPage: number;
    predicate: any;
    reverse: any;
    totalItems: number;
    links: any;
    currentAccount: any;
    eventSubscriber: Subscription;


    constructor(private jhiLanguageService: JhiLanguageService,
                private fundService: FundService,
                private alertService: AlertService,
                private parseLinks: ParseLinks,
                private principal: Principal,
                private eventManager: EventManager) {
        this.funds = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.jhiLanguageService.setLocations(['fund']);
    }


    ngOnInit(): void {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFunds();
    }


    loadAll() {
        this.fundService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }


    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.funds.push(data[i]);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    registerChangeInFunds() {
        this.eventSubscriber = this.eventManager.subscribe('fundListModification', (response) => this.reset());
    }

    reset() {
        this.page = 0;
        this.funds = [];
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Fund) {
        return item.id;
    }

}
