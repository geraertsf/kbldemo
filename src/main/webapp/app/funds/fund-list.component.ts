import {Component, OnInit} from '@angular/core';
import {JhiLanguageService} from 'ng-jhipster';
/**
 * Created by Francois Geraerts on 4/05/17.
 */
@Component({
    templateUrl: './fund-list.component.html'
})
export class FundListComponent implements OnInit {

    constructor(private jhiLanguageService: JhiLanguageService) {
        this.jhiLanguageService.setLocations(['fund']);
    }


    ngOnInit(): void {

    }
}
