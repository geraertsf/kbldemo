import { Category } from '../category';
import { SubCategory } from '../sub-category';
import { VniHistory } from '../vni-history';
import { Country } from '../country';
export class Fund {
    constructor(
        public id?: number,
        public range?: string,
        public name?: string,
        public category?: Category,
        public subCategory?: SubCategory,
        public vni?: VniHistory,
        public country?: Country,
    ) {
    }
}
