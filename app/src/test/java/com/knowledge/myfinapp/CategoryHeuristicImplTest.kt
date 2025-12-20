package com.knowledge.myfinapp

import com.knowledge.myfinapp.data.category.heuristic.CategoryHeuristicImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CategoryHeuristicImplTest {
    private lateinit var cut: CategoryHeuristicImpl

    @BeforeEach
    fun setup() {
        cut = CategoryHeuristicImpl()
    }

    @ParameterizedTest(name = "merchant=\"{0}\" → category=\"{1}\"")
    @MethodSource("merchantCategoryProvider")
    fun `categorize merchant correctly`(
        merchant: String,
        expectedCategory: String?
    ) {
        val result = cut.categorize(merchant)

        assertEquals(expectedCategory, result)
    }

    companion object {
        @JvmStatic
        fun merchantCategoryProvider(): Stream<Arguments> =
            Stream.of(
                Arguments.of("IFOOD BURGER KING", "Food"),
                Arguments.of("iFoOd mc donalds", "Food"),
                Arguments.of("UBER TRIP SAO PAULO", "Transport"),
                Arguments.of("AMAZON MARKETPLACE", "Shopping"),
                Arguments.of("PIZZARIA DONA MARIA", "Food"),
                Arguments.of("POSTO IPIRANGA", null),
                Arguments.of("", null),
                Arguments.of("   ", null)
            )
    }
}
