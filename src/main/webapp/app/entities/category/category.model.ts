import { Fund } from '../fund';
export class Category {
    constructor(
        public id?: number,
        public name?: string,
        public fund?: Fund,
    ) {
    }
}
