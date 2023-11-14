
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.hamcrest.MatcherAssert.assertThat

class TestCode{

    private val logger = LoggerFactory.getLogger( TestCode::class.java )

    @Test
    fun test() {
        val test = 50
        val res = assertThat(test, equalTo(50))
        logger.info("res =======> {}", res)
    }
}
