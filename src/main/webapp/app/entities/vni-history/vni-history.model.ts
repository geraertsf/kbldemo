import { Fund } from '../fund';
export class VniHistory {
    constructor(
        public id?: number,
        public date?: any,
        public value?: number,
        public fund?: Fund,
    ) {
    }
}
