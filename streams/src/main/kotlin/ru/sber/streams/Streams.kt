package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex()
        .filter { listItem -> listItem.index % 3 == 0 }
        .sumOf { listItem -> listItem.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1)) { pair ->
        Pair(pair.second, pair.first + pair.second)
    }.map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map { customer -> customer.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = customers.flatMap { customer ->
    customer.orders
        .flatMap { order -> order.products }
}.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxByOrNull { customer ->
    customer.orders.count()
}


// 6. Получить самый дорогой продукт, когда-либо приобретенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders.flatMap { order ->
    order.products
}.maxByOrNull { product -> product.price }


// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    customers.map { customer ->
        customer.city to customer.orders
            .filter { order -> order.isDelivered }
            .flatMap { order -> order.products }
            .count()
    }
        .groupBy { city -> city.first }
        .map { it -> it.key to it.value.sumOf { it.second } }.toMap()


// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    customers.groupBy { customer -> customer.city }
        .mapValues { customer ->
            customer.value
                .flatMap { it.orders }
                .flatMap { it.products }
                .groupingBy { it }
                .eachCount()
                .maxByOrNull { it.value }!!.key
        }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = customers.fold(allOrderedProducts()) { result, customer ->
    result.intersect(customer.orders.flatMap { customer -> customer.products }.toSet())
}

