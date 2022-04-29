package info.galudisu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IndividualTest {

  @Test
  fun `Secondary constructor using companion object`() {
    val subscribingIndividual = Individual.newSubscribingUser("bob@gmail.com")
    val facebookIndividual = Individual.newFacebookUser(4)

    assertEquals("bob", subscribingIndividual.nickname)
    assertEquals("4", facebookIndividual.nickname)
  }
}
