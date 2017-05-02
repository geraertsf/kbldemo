import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { KbldemoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VniHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/vni-history/vni-history-detail.component';
import { VniHistoryService } from '../../../../../../main/webapp/app/entities/vni-history/vni-history.service';
import { VniHistory } from '../../../../../../main/webapp/app/entities/vni-history/vni-history.model';

describe('Component Tests', () => {

    describe('VniHistory Management Detail Component', () => {
        let comp: VniHistoryDetailComponent;
        let fixture: ComponentFixture<VniHistoryDetailComponent>;
        let service: VniHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KbldemoTestModule],
                declarations: [VniHistoryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VniHistoryService,
                    EventManager
                ]
            }).overrideComponent(VniHistoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VniHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VniHistoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VniHistory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.vniHistory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
