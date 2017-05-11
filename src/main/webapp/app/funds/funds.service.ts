import {Injectable} from '@angular/core';
import {DateUtils} from 'ng-jhipster';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
/**
 * Created by Francois Geraerts on 11/05/17.
 */
@Injectable()
export class FundsService {

    private resourceUrl = 'api/vni-histories';

    constructor(private http: Http, private dateUtils: DateUtils) {
    }

    queryVni(fundId: number): Observable<Response> {
        return this.http.get(`${this.resourceUrl}/fund/${fundId}`)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].date = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].date);
        }
        res._body = jsonResponse;
        return res;
    }
}
