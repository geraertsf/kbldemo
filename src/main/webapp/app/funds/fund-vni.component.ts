import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {VniHistory} from '../entities/vni-history/vni-history.model';
import {Subscription} from 'rxjs/Subscription';
/**
 * Created by Francois Geraerts on 11/05/17.
 */
@Component({
    templateUrl: './fund-vni.component.html'
})
export class FundVniComponent implements OnInit{

    vniHistories: VniHistory[];
    private subscription: Subscription;


    constructor(private route: ActivatedRoute){}


    ngOnInit(): void {
        console.log('Initialization of FundVniComponent')

        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });

    }

    private load(id: number) {
        console.log(`Load vni for fund ${id}`);
        //Load the vni Histories

    }
}
