import { Fund } from '../fund';
export class SubCategory {
    constructor(
        public id?: number,
        public name?: string,
        public fund?: Fund,
    ) {
    }
}
