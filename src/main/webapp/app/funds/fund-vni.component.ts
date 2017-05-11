import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {VniHistory} from '../entities/vni-history/vni-history.model';
import {Subscription} from 'rxjs/Subscription';
import {AlertService, JhiLanguageService} from 'ng-jhipster';
import {FundsService} from './funds.service';
import {Response} from '@angular/http';

/**
 * Created by Francois Geraerts on 11/05/17.
 */
@Component({
    templateUrl: './fund-vni.component.html'
})
export class FundVniComponent implements OnInit, OnDestroy {

    vniHistories: VniHistory[];
    private subscription: Subscription;
    private fundId: number;
    predicate: any;
    reverse: any;

    constructor(private route: ActivatedRoute,
                private jhiLanguageService: JhiLanguageService,
                private fundsService: FundsService,
                private alertService: AlertService) {
        this.jhiLanguageService.setLocations(['vniHistory']);
        this.predicate = 'id';
        this.reverse = true;

    }

    ngOnInit(): void {
        console.log('Initialization of FundVniComponent');

        this.subscription = this.route.params.subscribe((params) => {
            this.fundId = params['id'];
            this.loadAll(this.fundId);
        });

    }

    ngOnDestroy(): void {
    }

    private loadAll(id: number) {
        console.log(`Load vni for fund ${id}`);
        // Load the vni Histories
        this.fundsService.queryVni(id).subscribe(
            (res: Response) => {
                this.vniHistories = res.json();
            },
            (res: Response) => this.onError(res.json())
        );

    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    reset() {
        this.loadAll(this.fundId);
    }

    previousState() {
        window.history.back();
    }
}
