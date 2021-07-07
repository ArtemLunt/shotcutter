export type ComparatorPredicate = (left: any, right: any) => boolean;

export abstract class Comparators {

  static deepComparePr: ComparatorPredicate = (left, right) => left === right || (
    JSON.stringify(left) === JSON.stringify(right)
  )

}
