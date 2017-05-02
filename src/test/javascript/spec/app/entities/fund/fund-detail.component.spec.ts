import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { KbldemoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FundDetailComponent } from '../../../../../../main/webapp/app/entities/fund/fund-detail.component';
import { FundService } from '../../../../../../main/webapp/app/entities/fund/fund.service';
import { Fund } from '../../../../../../main/webapp/app/entities/fund/fund.model';

describe('Component Tests', () => {

    describe('Fund Management Detail Component', () => {
        let comp: FundDetailComponent;
        let fixture: ComponentFixture<FundDetailComponent>;
        let service: FundService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KbldemoTestModule],
                declarations: [FundDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FundService,
                    EventManager
                ]
            }).overrideComponent(FundDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FundDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FundService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Fund(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fund).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
