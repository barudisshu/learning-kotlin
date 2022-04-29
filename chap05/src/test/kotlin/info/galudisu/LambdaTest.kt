package info.galudisu

import info.galudisu.Lambda.alphabet1
import info.galudisu.Lambda.alphabet2
import org.junit.jupiter.api.Test
import kotlin.test.assertContains

class LambdaTest {

  @Test
  fun `lambda with receivers - with`() {
    assertContains(alphabet1(), "alphabet")
  }

  @Test
  fun `lambda with receivers - apply`() {
    assertContains(alphabet2(), "alphabet")
  }
}
