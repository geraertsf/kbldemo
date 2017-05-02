import { Fund } from '../fund';
export class Country {
    constructor(
        public id?: number,
        public countryName?: string,
        public fund?: Fund,
    ) {
    }
}
