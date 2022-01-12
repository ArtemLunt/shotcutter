export function omitNullishFields<T = any>(obj: T): Partial<T> {
  return !!obj
    ? Object.fromEntries(
      Object.entries(obj)
        .filter(([_, value]) => !!value)
    ) as any
    : obj;
}
