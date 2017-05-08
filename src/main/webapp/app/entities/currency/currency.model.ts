import { Fund } from '../fund';
export class Currency {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public fund?: Fund,
    ) {
    }
}
