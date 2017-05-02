import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Fund } from './fund.model';
import { FundPopupService } from './fund-popup.service';
import { FundService } from './fund.service';
import { Category, CategoryService } from '../category';
import { SubCategory, SubCategoryService } from '../sub-category';
import { Country, CountryService } from '../country';

@Component({
    selector: 'jhi-fund-dialog',
    templateUrl: './fund-dialog.component.html'
})
export class FundDialogComponent implements OnInit {

    fund: Fund;
    authorities: any[];
    isSaving: boolean;

    categories: Category[];

    subcategories: SubCategory[];

    countries: Country[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private fundService: FundService,
        private categoryService: CategoryService,
        private subCategoryService: SubCategoryService,
        private countryService: CountryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['fund']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
        this.subCategoryService.query().subscribe(
            (res: Response) => { this.subcategories = res.json(); }, (res: Response) => this.onError(res.json()));
        this.countryService.query().subscribe(
            (res: Response) => { this.countries = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fund.id !== undefined) {
            this.fundService.update(this.fund)
                .subscribe((res: Fund) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.fundService.create(this.fund)
                .subscribe((res: Fund) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Fund) {
        this.eventManager.broadcast({ name: 'fundListModification', content: 'OK'});
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

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }

    trackSubCategoryById(index: number, item: SubCategory) {
        return item.id;
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-fund-popup',
    template: ''
})
export class FundPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fundPopupService: FundPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.fundPopupService
                    .open(FundDialogComponent, params['id']);
            } else {
                this.modalRef = this.fundPopupService
                    .open(FundDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
