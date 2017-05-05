import { Fund } from '../fund';
export class Country {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public fund?: Fund,
    ) {
    }
}
