# Changelog

## Changes in this commit

- Change `Order` to `record` to make it easier to handle
- Refactor loop into a single method
- Refactor pancake creation into a factory → simplify using an `Enum` with ingredients
- Make collections `static` to avoid multiple instantiations
- Make collections thread-safe to support access from different threads
- When returning `completedOrders` / `preparedOrders` sets, return a `Set.copyOf()` to prevent external modifications

## Proposed future improvements

- Consider splitting PancakeService into two separate services: `OrderService` and `PancakeService`
- Improve tests by evaluating multiple orders
- Improve tests using Mockito `spy` to avoid expose extra methods that are used only in tests
- Improve logging written to the console.
