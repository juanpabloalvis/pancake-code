# Changelog

## Changes in this commit

- Change `Order` to `record` to make it easier to handle
- Refactor loop into a single method
- Refactor pancake creation into a factory → simplify using an `Enum` with ingredients
- Make collections `static` to avoid multiple instantiations
- Make collections thread-safe to support access from different threads
- When returning `completedOrders` / `preparedOrders` sets, return a `Set.copyOf()` to prevent external modifications

## Proposed future improvements

- Consider splitting `Order` and `Recipes` into two separate services
- Improve tests by evaluating multiple orders
- Improve tests using Mockito `spy` to avoid extra methods used only in tests
- Improve logging written to the console.