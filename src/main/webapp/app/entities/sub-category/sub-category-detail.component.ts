import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { SubCategory } from './sub-category.model';
import { SubCategoryService } from './sub-category.service';

@Component({
    selector: 'jhi-sub-category-detail',
    templateUrl: './sub-category-detail.component.html'
})
export class SubCategoryDetailComponent implements OnInit, OnDestroy {

    subCategory: SubCategory;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private subCategoryService: SubCategoryService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['subCategory']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSubCategories();
    }

    load(id) {
        this.subCategoryService.find(id).subscribe((subCategory) => {
            this.subCategory = subCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSubCategories() {
        this.eventSubscriber = this.eventManager.subscribe('subCategoryListModification', (response) => this.load(this.subCategory.id));
    }
}
